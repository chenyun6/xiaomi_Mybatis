package com.qf.web.servlet;


import com.github.pagehelper.PageInfo;
import com.qf.pojo.Goods;
import com.qf.service.GoodsService;
import com.qf.utils.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GoodsServlet",value = "/goodsservlet")
public class GoodsServlet extends BaseServlet {
    private ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    private GoodsService goodsService = context.getBean("goodsServiceImpl",GoodsService.class);
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response){
        //GoodsService goodsService = new GoodsServiceImpl();
        String client_typeId = request.getParameter("typeId");
        String client_pageNum = request.getParameter("pageNum");
        String client_pageSize = request.getParameter("pageSize");
        int pageNum=1;
        int pageSize=8;
        if(StringUtils.stringJudge(client_pageNum)){
            pageNum=Integer.parseInt(client_pageNum);
            if(pageNum<1){
                pageNum=1;
            }
        }
        if(StringUtils.stringJudge(client_pageSize)){
            pageSize=Integer.parseInt(client_pageSize);
            if(pageSize<1){
                pageSize=8;
            }
        }
        try {
            //String typeId= "typeId=" + client_typeId;
            //PageBean pageBean = goodsService.findByTypeId(Integer.parseInt(client_typeId), pageNum, pageSize);
            PageInfo<Goods> pageBean = goodsService.findByLimit(Integer.parseInt(client_typeId), pageNum, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("typeId", Integer.parseInt(client_typeId));
            return "goodsList.jsp";
        }catch (Exception e){
            return "redirect:/index.jsp";
        }
        //List<Goods> list = goodsService.findByTypeId(Integer.parseInt(typeId));

    }
    public String getGoodsById(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        Goods goods=goodsService.findById(Integer.parseInt(id));
        request.setAttribute("goods", goods);
        return "/goodsDetail.jsp";
    }
}
