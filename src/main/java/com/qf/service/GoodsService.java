package com.qf.service;

import com.github.pagehelper.PageInfo;
import com.qf.pojo.Goods;
import com.qf.pojo.PageBean;

public interface GoodsService {
    PageBean findByTypeId(int typeId, int pageNum, int pageSize);
    Goods findById(int id);
    PageInfo<Goods> findByLimit(int typeId, int pageNum, int pageSize);
}
