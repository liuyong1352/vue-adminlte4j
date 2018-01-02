package com.jd.vue.adminlte.model;

/**
 * Created by bjliuyong on 2017/12/21.
 */
public class Page {

    private int currentPage ;
    private int pageSize    ;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
