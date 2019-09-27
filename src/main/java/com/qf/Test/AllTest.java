package com.qf.Test;

import com.github.pagehelper.PageInfo;
import com.qf.mapper.CartMapper;
import com.qf.mapper.GoodsMapper;
import com.qf.pojo.Goods;
import com.qf.pojo.Od;
import com.qf.pojo.Order;
import com.qf.pojo.User;
import com.qf.service.GoodsService;
import com.qf.service.OrderService;
import com.qf.service.UserService;
import com.qf.utils.Mybatis_Utils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTest {
    @Test
    public void findById(){
        SqlSession sqlSession = Mybatis_Utils.openSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = mapper.findDataByid(1);
        System.out.println(goods);
    }
    @Test
    public void findGoodsByLimit(){
        SqlSession sqlSession = Mybatis_Utils.openSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("typeId", 1);
        map.put("pageNum", 0);
        map.put("pageSize", 5);
        List<Goods> dataByLimit = mapper.findDataByLimit(map);
        for (Goods data : dataByLimit) {
            System.out.println(data);
        }
    }
    @Test
    public void getDataCount(){
        SqlSession sqlSession = Mybatis_Utils.openSqlSession();
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        long dataCount = mapper.getDataCount(1);
        System.out.println("数据----"+dataCount);
    }
    @Test
    public void testCart(){
        SqlSession sqlSession = Mybatis_Utils.openSqlSession();
        CartMapper mapper = sqlSession.getMapper(CartMapper.class);
        BigDecimal totalMoney = mapper.getTotalMoney(2);
        System.out.println(totalMoney);
    }
    @Test
    public void checkLoginTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        User user = userService.chackLogin("陈云", "123456");
        System.out.println(user);
    }
    @Test
    public void checkUserTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        User user = userService.chackUser("陈云");
        System.out.println(user);
    }
    @Test
    public void addUserTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        userService.regist(new User(null, "haha", "123456", "12356451", "男", 1, 1, "12465"));

    }
    @Test
    public void findByUIdTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderService orderService= context.getBean("orderServiceImpl", OrderService.class);
        List<Order> orders = orderService.findByUId(7);
        System.out.println(orders.size());
    }
    @Test
    public void reduceOrderTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderService orderService = context.getBean("orderServiceImpl", OrderService.class);
        orderService.reduceOrder(new Order("565456198",7,new BigDecimal(20),"1",new Date(),7));
    }
    @Test
    public void orderDetialTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderService orderService = context.getBean("orderServiceImpl", OrderService.class);
        Od od = orderService.orderDetial("20190920162732330");
        System.out.println(od);
    }
    @Test
    public void findByLimit(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GoodsService goodsService = context.getBean("goodsServiceImpl", GoodsService.class);
        PageInfo<Goods> pageInfo = goodsService.findByLimit(1, 1, 2);
        System.out.println("===========");
    }
}
