package com.demo.utils;

import java.util.List;

public class Page<T> {

    private int total; // 总条数
    private int page;  // 当前页
    private int size;  // 每页展示的条数
    private List<T> pageDate;// 结果集

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getPageDate() {
        return pageDate;
    }

    public void setPageDate(List<T> pageDate) {
        this.pageDate = pageDate;
    }
}