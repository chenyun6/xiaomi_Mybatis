package com.qf.web.servlet;


import com.qf.pojo.Cart;
import com.qf.pojo.User;
import com.qf.service.CartService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "CartServlet",value = "/cartservlet")
public class CartServlet extends BaseServlet {
    private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private CartService cartService = context.getBean("cartServiceImpl",CartService.class);
    public String addCart(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/index.jsp";
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        //根据用户id和商品查找数据库有没有买过
        Cart cart=cartService.findByIdAndGoodsId(user.getId(),Integer.parseInt(goodsId));
        if(cart==null){
            //添加
            cart=new Cart(user.getId(),Integer.parseInt(goodsId),Integer.parseInt(number),null);
            cartService.addCartData(cart);
            return "/cartSuccess.jsp";
        }else {
            //更新数据
            cartService.updateNum(cart,number);
            return "/cartSuccess.jsp";
        }
    }
    public String getCart(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/index.jsp";
        }
        List<Cart> cart = cartService.findByUId(user.getId());
        request.getSession().setAttribute("cart", cart);
        return "/cart.jsp";
    }
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response){
        String client_number = request.getParameter("number");
        String client_goodsId = request.getParameter("goodsId");
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        int number = Integer.parseInt(client_number);
        int goodsId=Integer.parseInt(client_goodsId);
        //查询
        //CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByIdAndGoodsId(user.getId(), goodsId);
        if(cart!=null){
            if(number==0){
                //删除
                cartService.deleteById(cart.getId(),cart.getPid());
            }else{
                //更新
                BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));
                cart.setNum(cart.getNum()+number);
                cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
        }


        return null;
    }
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/index.jsp";
        }
        cartService.deleteByUId(user.getId());
        return null;
    }
}
