package com.qf.service.impl;

import com.qf.mapper.AddressMapper;
import com.qf.pojo.Address;
import com.qf.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> findByUid(int id) {
        List<Address> list = addressMapper.findByUid(id);
        return list;
    }

    @Override
    public void addAddress(Address address) {
        addressMapper.addAddress(address);
    }

    @Override
    public void deleteById(int id) {
        addressMapper.deleteById(id);
    }

    @Override
    public void updateById(Address address) {
        addressMapper.updateById(address);
    }

    @Override
    public void updateById(int uId, int id) {
        List<Address> list= addressMapper.findByUid(uId);
        for (Address address : list) {
            address.setLevel(0);
            addressMapper.updateById(address);
        }
        addressMapper.updateByAId(id);
    }

    @Override
    public Address findByUidAndId(int uId, int i) {
        Address byUidAndId = addressMapper.findByUidAndId(uId, i);
        return byUidAndId;
    }
}
