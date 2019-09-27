package com.qf.mapper;

import com.qf.pojo.Address;

import java.util.List;

public interface AddressMapper {
    List<Address> findByUid(int id);

    void addAddress(Address address);

    void deleteById(int id);

    void updateById(Address address);

    void updateByAId(int id);

    Address findByUidAndId(int uId, int i);
    Address findByAId(int oid);
}
