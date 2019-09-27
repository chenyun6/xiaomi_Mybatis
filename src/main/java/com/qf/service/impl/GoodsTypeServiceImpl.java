package com.qf.service.impl;

import com.qf.mapper.GoodsTypeMapper;
import com.qf.pojo.GoodsType;
import com.qf.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;
    @Override
    public List<GoodsType> findAllByTypeId(int leval) {
        List<GoodsType> list = goodsTypeMapper.findAllByLeval(leval);
        return list;
    }

    @Override
    public GoodsType findByid(Integer goodsType_id) {
        GoodsType goodsType = goodsTypeMapper.findById(goodsType_id);
        return goodsType;
    }
}
