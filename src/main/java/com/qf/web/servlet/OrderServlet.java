package com.qf.web.servlet;

import com.qf.pojo.*;
import com.qf.service.AddressService;
import com.qf.service.CartService;
import com.qf.service.OrderService;
import com.qf.utils.RandomUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet",value = "/orderservlet")
public class OrderServlet extends BaseServlet {
    private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private CartService cartService = context.getBean("cartServiceImpl",CartService.class);
    private OrderService orderService = context.getBean("orderServiceImpl",OrderService.class);
    private AddressService addressService = context.getBean("addressServiceImpl",AddressService.class);
    public String getOrderView(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        List<Address> addList = addressService.findByUid(user.getId());
        List<Cart> carts= cartService.findByUId(user.getId());
        request.setAttribute("addList", addList);
        request.setAttribute("carts", carts);
        return "/order.jsp";
    }
    public String addOrder(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "/login.jsp";
        }
        String aid = request.getParameter("aid");
        BigDecimal totalMoney=cartService.getTotalMoney(user.getId());

        Order order = new Order(RandomUtils.createOrderId(), user.getId(),totalMoney , "1", new Date(), Integer.parseInt(aid));

        request.getSession().setAttribute("order", order);
        try {
            orderService.reduceOrder(order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("<script type='text/javascript'>alert('出现异常');window.location='orderservlet?method=getOrderView'</script>");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public String deleteByid(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("oid");
        orderService.deleteById(id);
        return "redirect:/userservlet?method=getOrderList";
    }
    //确认收货
    public String changeStatus(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("oid");
        orderService.changeStatus(id,"4");
        return "redirect:/userservlet?method=getOrderList";
    }
    //订单详情
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response){
        String oid = request.getParameter("oid");
        Od od = orderService.orderDetial(oid);
        request.getSession().setAttribute("od", od);
        return "/orderDetail.jsp";
    }
}
