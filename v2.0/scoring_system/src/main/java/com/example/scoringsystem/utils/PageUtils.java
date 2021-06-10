package com.example.scoringsystem.utils;

import com.example.scoringsystem.bean.PageRequest;
import com.example.scoringsystem.bean.PageResult;
import com.github.pagehelper.PageInfo;

public class PageUtils {
    /**
     * @Description: 将分页信息封装到统一的接口
     * @Param: [pageRequest, pageInfo]
     * @return: com.example.scoring_system.bean.PageResult
     * @Date: 2021/5/1
     */
    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
