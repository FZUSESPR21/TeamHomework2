package com.example.scoringsystem.bean;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    //当前页码
    int pageNum;
    //每页数量
    int pageSize;
    //记录总数
    long totalSize;
    //页码总数
    int totalPages;
    List<?> content;
}
