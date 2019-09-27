package com.qf.mapper;

import com.qf.pojo.GoodsType;

import java.util.List;

public interface GoodsTypeMapper {
    List<GoodsType> findAllByLeval(int level);

    GoodsType findById(Integer goodsType_id);
}
