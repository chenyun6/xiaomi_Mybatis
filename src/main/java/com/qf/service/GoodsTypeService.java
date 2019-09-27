package com.qf.service;

import com.qf.pojo.GoodsType;

import java.util.List;

public interface GoodsTypeService {
    List<GoodsType> findAllByTypeId(int typeId);

    GoodsType findByid(Integer goodsType_id);
}
