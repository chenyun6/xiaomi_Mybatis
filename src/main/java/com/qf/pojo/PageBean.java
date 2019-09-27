package com.qf.pojo;

import java.util.List;

public class PageBean {
    private int pageNum;
    private int pageSize;
    private long dataCount;
    private int pageCount;
    private List<Goods> data;
    private int startPage;
    private int endPage;

    public PageBean(int pageNum, int pageSize, long dataCount, List<Goods> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.dataCount = dataCount;
        this.data = data;
        this.pageCount = (int) (dataCount%pageSize==0?dataCount/pageSize:dataCount/pageSize+1);
        this.startPage=pageNum-4;
        this.endPage=pageNum+5;
        if(pageNum<5){
            this.startPage=1;
            this.endPage=10;
        }
        if(pageNum>(pageCount-5)){
            this.startPage=pageCount-9;
            this.endPage = pageCount;
        }
        if(pageCount<10){
            this.startPage=1;
            this.endPage=pageCount;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Goods> getData() {
        return data;
    }

    public void setData(List<Goods> data) {
        this.data = data;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", dataCount=" + dataCount +
                ", pageCount=" + pageCount +
                ", data=" + data +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                '}';
    }
}
