package com.example.scoringsystem.bean;

import lombok.Data;

@Data
public class PageRequest {
    //当前页码
    int pageNum;
    //每页数量
    int pageSize;
}
