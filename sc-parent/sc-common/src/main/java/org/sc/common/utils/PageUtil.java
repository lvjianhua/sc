package org.sc.common.utils;

import org.sc.common.model.param.PageParam;
import org.sc.common.model.vo.Page;



/**
 * 分页工具类
 *
 * @author run
 * @create 2017-05-16 10:24
 **/
public class PageUtil {

    public static Page toPage(org.springframework.data.domain.Page pager) {
        Page result = new Page();
        result.setContent(pager.getContent());
        result.setTotalPage(pager.getTotalPages());
        result.setTotalElements(pager.getTotalElements());
        result.setPageSize(pager.getSize());
        result.setCurrentPage(pager.getNumber());
        return result;
    }

    /**
     * 将前端传过来的页数减1
     * @param pageParam
     */
    public static void setPage(PageParam pageParam) {
        if (pageParam.getPage() <= 0) {
            pageParam.setPage(0);
        } else {
            pageParam.setPage(pageParam.getPage() - 1);
        }
    }

}
