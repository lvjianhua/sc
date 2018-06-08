package org.sc.common.model.vo;



import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lll on 2017/5/4.
 */
public class Page<T> {
    private Integer pageSize;  // 一页多少行
    private Integer currentPage; // 当前页
    private Integer totalPage;   // 总页数
    private Long totalElements;  // 总数
    private List<T> content = new ArrayList<T>(); // 内容

    public Page() {
    }

    public Page(Page page) {
        this.pageSize = page.getPageSize();
        this.currentPage = page.getCurrentPage();
        this.totalPage = page.getTotalPage();
        this.totalElements = page.getTotalElements();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean hasContent() {
        return !CollectionUtils.isEmpty(this.content);
    }
}
