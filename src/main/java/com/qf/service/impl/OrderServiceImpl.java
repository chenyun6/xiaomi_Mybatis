package com.qf.service.impl;

import com.qf.mapper.AddressMapper;
import com.qf.mapper.CartMapper;
import com.qf.mapper.OrderMapper;
import com.qf.pojo.*;
import com.qf.service.AddressService;
import com.qf.service.GoodsService;
import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private AddressService addressService;
    @Override
    public List<Order> findByUId(int uId) {

        List<Order> orders = orderMapper.findByUid(uId);
        for (Order order : orders) {
            Address byUidAndid = addressService.findByUidAndId(uId, order.getAid());
            order.setAddress(byUidAndid);
        }
        return orders;
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceOrder(Order order) {

        orderMapper.addOrder(order);
        List<Cart> carts = cartMapper.findByUid(order.getUid());
        List<Orderdetail> list=new LinkedList<>();
        for (Cart cart : carts) {
            Orderdetail orderdetail = new Orderdetail(null, order.getId(), cart.getPid(), cart.getNum(), cart.getMoney());
            list.add(orderdetail);
        }
       // int a=10/0;
        cartMapper.deleteByUId(order.getUid());
        for (Orderdetail orderdetail : list) {
            orderMapper.addOrderDetdil(orderdetail);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String r6_order, String s) {

       orderMapper.updateStatus(r6_order, s);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {

        orderMapper.deleteDetailById(id);
        //int a= 10/0;
        orderMapper.deleteById(id);

    }

    @Override
    public void changeStatus(String id,String status) {
        orderMapper.updateStatus(id, status);
    }

    @Override
    public Od orderDetial(String oid) {
        Od od = new Od();
        Order order = orderMapper.findByAId(oid);
        Address address = addressMapper.findByAId(order.getAid());
        List<Orderdetail> orderdetails = orderMapper.findByOId(oid);
        od.setAddress(address);
        od.setOrder(order);
        List<Goods> goods = new LinkedList<>();
        for (Orderdetail orderdetail : orderdetails) {
            Goods goods1 = goodsService.findById(orderdetail.getPid());
            goods1.setOrderdetail(orderdetail);
            goods.add(goods1);
        }
        od.setList(goods);
        return od;
    }
}
