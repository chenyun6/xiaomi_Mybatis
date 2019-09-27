package com.qf.mapper;

import com.qf.pojo.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    Goods findDataByid(int id);

    List<Goods> findDataByLimit(Map<String,Object> map);
    long getDataCount(int typeId);

    List<Goods> findByLimit(int typeId);

}
