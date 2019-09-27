package com.qf.service;

import com.qf.pojo.Address;

import java.util.List;

public interface AddressService {
    List<Address> findByUid(int id);

    void addAddress(Address address);

    void deleteById(int id);

    void updateById(Address address);

    void updateById(int uId, int id);

    Address findByUidAndId(int uId, int i);
}
