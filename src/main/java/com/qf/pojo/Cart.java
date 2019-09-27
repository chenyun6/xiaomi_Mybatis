package com.qf.pojo;

import java.math.BigDecimal;

/*
`id` int(11) NOT NULL,
  `pid` int(11) NOT NULL DEFAULT '0',
  `Num` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
 */
public class Cart {
    private Integer id;
    private Integer pid;
    private Integer num;
    private BigDecimal money;
    private Goods goods;


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Cart(Integer id, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.pid = pid;
        this.num = num;
        this.money = money;


    }

    public Cart() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "CartMapper{" +
                "id=" + id +
                ", pid=" + pid +
                ", Num=" + num +
                ", money=" + money +
                '}';
    }
}
