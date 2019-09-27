package com.qf.web.servlet;

import com.alibaba.fastjson.JSON;
import com.qf.pojo.Goods;
import com.qf.pojo.GoodsType;
import com.qf.service.GoodsTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoodsTypeServlet",value = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private GoodsTypeService goodsTypeService = (GoodsTypeService) context.getBean("goodsTypeServiceImpl");
    public List<Goods> goodstypelist(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json;charset=utf-8");
        //GoodsTypeService goodsService = new GoodsTypeServiceImpl();
        List<GoodsType> list = goodsTypeService.findAllByTypeId(1);
        String s = JSON.toJSONString(list);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
