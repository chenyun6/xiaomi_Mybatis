package com.qf.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter("method");
        try {
            Method method = this.getClass().getMethod(requestMethod, HttpServletRequest.class, HttpServletResponse.class);
            String data =(String) method.invoke(this, request,response);
            if(data!=null&&data.trim().length()!=0){
                if(data.startsWith("redirect:")){
                    //重定向
                    response.sendRedirect(request.getContextPath()+data.split(":")[1]);
                }else {
                    //转发
                    request.getRequestDispatcher(data).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
