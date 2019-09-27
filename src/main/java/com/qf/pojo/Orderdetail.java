package com.qf.pojo;

import java.math.BigDecimal;

/*
`id` int(11) NOT NULL AUTO_INCREMENT,
  `Oid` varchar(100) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `Money` int(11) DEFAULT NULL,
 */
public class Orderdetail {
    private Integer id;
    private String oid;
    private Integer pid;
    private Integer num;
    private BigDecimal money;

    public Orderdetail() {
    }

    public Orderdetail(Integer id, String oid, Integer pid, Integer num, BigDecimal money) {
        this.id = id;
        this.oid = oid;
        this.pid = pid;
        this.num = num;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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
        return "Orderdetail{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", num=" + num +
                ", money=" + money +
                '}';
    }
}
