package com.qf.service;

import com.qf.pojo.Od;
import com.qf.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByUId(int id);

    void reduceOrder(Order order);

    void updateStatus(String r6_order, String s);

    void deleteById(String id);

    void changeStatus(String id, String status);

    Od orderDetial(String oid);
}
