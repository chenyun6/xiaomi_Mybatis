package com.qf.mapper;

import com.qf.pojo.Order;
import com.qf.pojo.Orderdetail;

import java.util.List;

public interface OrderMapper {
    List<Order> findByUid(int id);

    void addOrder(Order order);

    void updateStatus(String r6_order, String s);

    void deleteById(String id);

    void addOrderDetdil(Orderdetail orderdetail);

    Order findByAId(String oid);

    List<Orderdetail> findByOId(String oid);

    void deleteDetailById(String id);
}
