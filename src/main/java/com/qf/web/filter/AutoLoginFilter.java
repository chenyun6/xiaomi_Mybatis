package com.qf.web.filter;

import com.qf.pojo.ResultMsg;
import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.UserSessionMap;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

@WebFilter(filterName = "AutoLoginFilter",value = "/index.jsp")
public class AutoLoginFilter implements Filter {
    private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private UserService userService = context.getBean("userServiceImpl",UserService.class);
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user1 = (User) request.getSession().getAttribute("user");
        if(user1!=null){
            chain.doFilter(req, resp);
            return;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("userInfo")){
                    String value = cookie.getValue();
                    byte[] decode = Base64.getDecoder().decode(value);
                    String userinfos= new String(decode);
                    String username=userinfos.split("#")[0];
                    String password=userinfos.split("#")[1];
                    User user = userService.chackLogin(username, password);
                    if(user!=null){

                        if(user.getFlag()==1){

                            //ResultMsg resultMsg;
                            //判断map集合中是否存在此session
                            HttpSession session = UserSessionMap.userSession.get(username + password);
                            if(session!=null){    //不为空则有  登录过
                                //在登录过的session中存入信息
                                session.setAttribute("resultMsg", ResultMsg.faliure("该账号在其它地点登录，请重新登录！"));
                            }
                            //将账号密码保存在session中
                            request.getSession().setAttribute("checkUserInfo", username+password);
                            //向map集合在添加元素
                            UserSessionMap.userSession.put(username+password, request.getSession());


                            request.getSession().setAttribute("user", user);
                            chain.doFilter(req, resp);
                            return;
                        }
                    }
                    else{
                        Cookie cookie1 = new Cookie("userInfo", "");
                        cookie1.setMaxAge(0);
                        cookie1.setHttpOnly(true);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
