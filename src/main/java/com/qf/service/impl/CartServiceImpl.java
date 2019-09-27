package com.qf.service.impl;

import com.qf.mapper.CartMapper;
import com.qf.pojo.Cart;
import com.qf.pojo.Goods;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GoodsService goodsService;
    @Override
    public Cart findByIdAndGoodsId(Integer id, int parseInt) {
        Cart goodsId = cartMapper.findDataByIdAndGoodsId(id, parseInt);
        return goodsId;
    }

    @Override
    public void addCartData(Cart cart) {
        Goods byId = goodsService.findById(cart.getPid());
        BigDecimal price = byId.getPrice();
        try {
            cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
            cartMapper.insertData(cart);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加新订单失败！", e);
        }
    }

    @Override
    public void updateNum(Cart cart,String number) {
        try {
            cart.setNum(Integer.parseInt(number)+cart.getNum());
            Goods byId = goodsService.findById(cart.getPid());
            BigDecimal price = byId.getPrice();
            cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
            cartMapper.update(cart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("修改订单失败！", e);
        }
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> carts = cartMapper.findAll();
        for (Cart cart : carts) {
            Goods byId = goodsService.findById(cart.getPid());
            cart.setGoods(byId);
        }
        return carts;
    }

    @Override
    public void deleteById(int id,int pid) {
        cartMapper.deleteById(id,pid);
    }

    @Override
    public void deleteByUId(int id) {
        cartMapper.deleteByUId(id);
    }

    @Override
    public void update(Cart cart) {
        cartMapper.update(cart);
    }

    @Override
    public List<Cart> findByUId(int id) {
        List<Cart> carts = cartMapper.findByUid(id);
        for (Cart cart : carts) {
            Goods byId = goodsService.findById(cart.getPid());
            cart.setGoods(byId);
        }
        return carts;
    }

    @Override
    public BigDecimal getTotalMoney(int uId) {
        BigDecimal totalMoney = cartMapper.getTotalMoney(uId);
        return totalMoney;
    }
}
