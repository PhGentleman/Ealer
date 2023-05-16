package org.ph.ealer.bean;

import java.util.List;

public class Page {
    private long totalRecords;
    private long pageRecords;
    private long totalPages;
    private long currentPage;
    private List<Picture> pictures;

    // 当前页不能小于1，不能大于最大页数
    // 计算总页数
    public Page(long pageRecords, long currentPage, long totalRecords, List<Picture> pictures) {
        this.pageRecords = pageRecords;
        this.currentPage = currentPage;
        this.totalRecords = totalRecords;
        // 计算总页数
        this.totalPages = (totalRecords % pageRecords == 0) ? (totalRecords / pageRecords)
                : (totalRecords / pageRecords + 1);
        if (this.totalPages <= 0) {
            this.totalPages = 1;
        }
        // 当前页的逻辑
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }
        if (this.currentPage > totalPages) {
            this.currentPage = totalPages;
        }
        this.pictures = pictures;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getPageRecords() {
        return pageRecords;
    }

    public void setPageRecords(long pageRecords) {
        this.pageRecords = pageRecords;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
