package com.qf.pojo;

import java.util.List;

public class Od {
    private Order order;
    private Address address;
    private List<Goods> list;

    public Od(Order order, Address address, List<Goods> list) {
        this.order = order;
        this.address = address;
        this.list = list;
    }

    public Od() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Od{" +
                "order=" + order +
                ", address=" + address +
                ", list=" + list +
                '}';
    }
}
