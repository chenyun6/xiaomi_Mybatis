package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.mapper.GoodsMapper;
import com.qf.pojo.Goods;
import com.qf.pojo.GoodsType;
import com.qf.pojo.PageBean;
import com.qf.service.GoodsService;
import com.qf.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Override
    public PageBean findByTypeId(int typeId, int pageNum, int pageSize) {
            Map<String,Object> map = new HashMap<>();
            map.put("typeId", typeId);
            map.put("pageNum", (pageNum-1)*pageSize);
            map.put("pageSize", pageSize);
            List<Goods> data = goodsMapper.findDataByLimit(map);
            long dataCount=goodsMapper.getDataCount(typeId);
            PageBean pageBean = new PageBean(pageNum, pageSize, dataCount, data);
             return pageBean;
    }

    @Override
    public Goods findById(int id) {
        Goods dataByid = goodsMapper.findDataByid(id);
        Integer goodsType_id = dataByid.getTypeid();
        GoodsType goodsType=goodsTypeService.findByid(goodsType_id);
        dataByid.setGoodsType(goodsType);
        return dataByid;
    }

    @Override
    public PageInfo<Goods> findByLimit(int typeId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goods = goodsMapper.findByLimit(typeId);
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goods);
        return pageInfo;
    }
}
