package com.qf.web.servlet;

import cn.dsna.util.images.ValidateCode;
import com.alibaba.fastjson.JSON;
import com.qf.pojo.Address;
import com.qf.pojo.Order;
import com.qf.pojo.ResultMsg;
import com.qf.pojo.User;
import com.qf.service.AddressService;
import com.qf.service.OrderService;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.utils.RandomUtils;
import com.qf.utils.StringUtils;
import com.qf.utils.UserSessionMap;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserServlet",value = "/userservlet")
public class UserServlet extends BaseServlet {
   private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
   private UserService userService = context.getBean("userServiceImpl",UserService.class);
   private AddressService addressService = context.getBean("addressServiceImpl",AddressService.class);
   private OrderService orderService = context.getBean("orderServiceImpl",OrderService.class);
    public String register(HttpServletRequest request, HttpServletResponse response){
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            String email = request.getParameter("email");

            if (!StringUtils.stringJudge(password)){
                request.setAttribute("registerMsg", "输入密码不能为空！");
                return "/register.jsp";
            }
            if (!StringUtils.stringJudge(repassword)){
                request.setAttribute("registerMsg", "确认密码不能为空！");
                return "/register.jsp";
            }
            if (!StringUtils.stringJudge(email)){
                request.setAttribute("registerMsg", "邮箱不能为空！");
                return "/register.jsp";
            }
            if (!StringUtils.stringEqual(password, repassword)) {
                request.setAttribute("registerMsg", "两次密码输入不一致！");
                return "/register.jsp";
            }
            user.setRole(0);     //会员
            user.setFlag(0);      //激活未激活
            user.setCode(RandomUtils.createAction());
            UserService userService =new UserServiceImpl();
            userService.regist(user);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerMsg", "注册失败！");
        }
        return "/registerSuccess.jsp";
    }
    public String login(HttpServletRequest request, HttpServletResponse response){
       String username = request.getParameter("username");
       String password = request.getParameter("password");
        String auto = request.getParameter("auto");
        if(!StringUtils.stringJudge(username)){
            request.setAttribute("msg", "用户名不能为空！");
            return "/login.jsp";
       }
        if(!StringUtils.stringJudge(password)){
            request.setAttribute("msg", "密码不能为空！");
            return "/login.jsp";
        }
        //UserService userService = new UserServiceImpl();
        User user = userService.chackLogin(username, password);
        if(user!=null){
            if(user.getFlag()==0){
                request.setAttribute("msg", "账户未激活,请激活！");
                return "/login.jsp";
            }
            if(user.getRole()==1){
                String userInfo = username+"#"+password;
                request.getSession().setAttribute("user",user);
                if(auto!=null){
                    Cookie cookie = new Cookie("userInfo",Base64.getEncoder().encodeToString(userInfo.getBytes()));
                    cookie.setMaxAge(60*60*24*14);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
                //判断UserSessionMap集合中是否存在session
                HttpSession session = UserSessionMap.userSession.get(username + password);
                if(session!=null){    // 登录过
                    //在登录过的session中存入resultMsg对象  此时success为false，message值为下面所赋的
                   session.setAttribute("resultMsg", ResultMsg.faliure("该账号在其它地点登录，请重新登录！"));
                }
                //将账号密码保存在session中
                request.getSession().setAttribute("checkUserInfo", username+password);
                //将当前会话的session存入UserSessionMap集合中
                UserSessionMap.userSession.put(username+password, request.getSession());
                //重定向
                return "redirect:/index.jsp";
            }else {
                request.setAttribute("msg", "此用户为管理员账号，不能登录！");
                return "/login.jsp";
            }

        }else {
            request.setAttribute("msg", "账号密码输入有误,请重新登录！");
            return "/login.jsp";
        }
    }
    public void checkUserName(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
       // UserService userService = new UserServiceImpl();
        User user = userService.chackUser(username);
        try {
            if(username==null||username.trim().length()==0){
                response.getWriter().write("1");
            }
            else if(user!=null){
                response.getWriter().write("1");
            }else {
                response.getWriter().write("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getValiCode(HttpServletRequest request, HttpServletResponse response){
        ValidateCode vc = new ValidateCode(250, 60, 4, 30);
        String code = vc.getCode();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            vc.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
    }
    public void checkValiCode(HttpServletRequest request, HttpServletResponse response){
        String usercode = request.getParameter("code");
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        try {
            if(!StringUtils.stringJudge(usercode)){
                response.getWriter().write("1");
            }
            else if(!usercode.equalsIgnoreCase(code)){
                response.getWriter().write("1");
            }else {
                response.getWriter().write("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String code(HttpServletRequest request, HttpServletResponse response){
        ValidateCode validateCode = new ValidateCode(100, 40, 4, 20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("vcode", code);
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientcode=request.getParameter("code");
        String servercode= (String) request.getSession().getAttribute("vcode");
        if(!StringUtils.stringJudge(clientcode)){
            return null;
        }
        if(clientcode.equalsIgnoreCase(servercode)){
            response.getWriter().write("0");
        }else {
            response.getWriter().write("1");
        }

        return null;
    }
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("userInfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        UserSessionMap.userSession.remove(request.getSession().getAttribute("checkUserInfo"));
        //清除session
        request.getSession().invalidate();
        return "redirect:/index.jsp";
    }
    public String getAddress(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        //AddressService addressService = new AddressServiceImpl();
        //查询根据用户uid查询地址
        List<Address> addList = addressService.findByUid(user.getId());
        request.getSession().setAttribute("addList", addList);
        return "redirect:/self_info.jsp";
    }
    public String addAddress(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if(name==null||name.trim().length()==0){
            request.getSession().setAttribute("msg", "姓名不能为空！");
            return "/userservlet?method=getAddress";
        }
        if(phone==null||phone.trim().length()==0){
            request.getSession().setAttribute("msg", "手机号不能为空！");
            return "/userservlet?method=getAddress";
        }
        if(detail==null||detail.trim().length()==0){
            request.getSession().setAttribute("msg", "地址不能为空！");
            return "/userservlet?method=getAddress";
        }
        Address address = new Address(null, detail, name, phone, user.getId(), 0);
        //AddressService addressService = new AddressServiceImpl();
        try {
            addressService.addAddress(address);
           // "<script type='text/javascript'>alert('电话不能为空');window.location='userservlet?method=getAddress'</script>"
            response.getWriter().write("<script type='text/javascript'>alert('添加成功！');window.location='userservlet?method=getAddress'</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String deleteAddress(HttpServletRequest request, HttpServletResponse response){
        String client_id = request.getParameter("id");
       // AddressService addressService = new AddressServiceImpl();
        addressService.deleteById(Integer.parseInt(client_id));
        return "/userservlet?method=getAddress";
    }
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "director:/login.jsp";
        }
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detial = request.getParameter("detail");
        String id = request.getParameter("id");
        if(name==null||name.trim().length()==0){
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        if(phone==null||phone.trim().length()==0){
            response.getWriter().write("<script type='text/javascript'>alert('电话不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        if(detial==null||detial.trim().length()==0){
            response.getWriter().write("<script type='text/javascript'>alert('地址不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        Address address = new Address(Integer.parseInt(id), detial, name, phone, user.getId(), 0);
        //AddressService addressService = new AddressServiceImpl();
        try {
            addressService.updateById(address);
            return "userservlet?method=getAddress";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        //AddressService addressService = new AddressServiceImpl();
        addressService.updateById(user.getId(),Integer.parseInt(id));
        return "userservlet?method=getAddress";
    }
    public String getOrderList(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        //OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.findByUId(user.getId());
        request.getSession().setAttribute("orderList", orders);
        return "redirect:/orderList.jsp";
    }
    public void chackLogin(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //response.setContentType("application/json");
            ResultMsg resultMsg = (ResultMsg) request.getSession().getAttribute("resultMsg");
            if(resultMsg==null){
                //下面产生的resultMsg对象success为true，msessage值为null;
                resultMsg=ResultMsg.success(null);
            }
            response.getWriter().write(JSON.toJSONString(resultMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        String userInfo = (String) session.getAttribute("checkUserInfo");
        if(UserSessionMap.userSession.get(userInfo)==session){
            UserSessionMap.userSession.remove(userInfo);
        }
        //使session失效
        session.invalidate();

        Cookie cookie1 = new Cookie("userInfo", "");
        cookie1.setMaxAge(0);
        cookie1.setHttpOnly(true);
        cookie1.setPath("/");
        response.addCookie(cookie1);
        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }
    public String chackLoginOut(HttpServletRequest request, HttpServletResponse response){
            String chackUserInfo = (String) request.getSession().getAttribute("checkUserInfo");
           //防止当前最新登录页面的session被移除
        synchronized (UserSessionMap.class) {
            if(request.getSession()!= UserSessionMap.userSession.get(chackUserInfo)){
                Cookie cookie = new Cookie("userInfo", "");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                //让session失效
                request.getSession().invalidate();
                return "redirect:/login.jsp";
            }
            return "redirect:/index.jsp";
        }
        //UserSessionMap.userSession.remove((String) request.getSession().getAttribute("checkUserInfo"));
            //UserSessionMap.userSession.put((String) request.getSession().getAttribute("checkUserInfo"), null);

    }

}
