package com.qf.mapper;

import com.qf.pojo.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartMapper {
    Cart findDataByIdAndGoodsId(Integer id, int parseInt);

    void insertData(Cart cart);

    void update(Cart cart);

    List<Cart> findAll();

    void deleteById(int id,int pid);
    void deleteByUId(int id);

    List<Cart> findByUid(int id);

    BigDecimal getTotalMoney(int uId);
}
