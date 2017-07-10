package com.agm.utils;

import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by David-Yang on 2017/1/9.
 *
 * @description 该类用于分页，可以通过HttpServletRequest来构造基于客户端传参的构造方式，也可以基于直接填写相关参数的构造方法
 *
 * 传递给mybatis时，调用getRowBound（）方法，返回一个RowBounds即可。
 */
public class Pagination {
    private int currentPage=1;
    private int limit=10;

    public Pagination() {
    }

    public Pagination(HttpServletRequest request) {
        String cur = request.getParameter("currentPage");
        String lim = request.getParameter("pageSize");
        this.currentPage = cur == null ? 0 : Integer.parseInt(cur);
        this.limit = lim == null ? 0 : Integer.parseInt(lim);
    }

    public Pagination(int currentPage, int limit) {
        this.currentPage = currentPage;
        this.limit = limit;
    }

    /**
     * 用于Mybatis分页，mybatis分页使用的是RowBounds类
     *
     * @return
     */
    public RowBounds getRowBound() {
        return new RowBounds((currentPage -1)* limit , limit);
    }
}
