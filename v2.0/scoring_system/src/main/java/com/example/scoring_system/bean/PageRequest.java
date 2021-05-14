package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class PageRequest {
    //当前页码
    int pageNum;
    //每页数量
    int pageSize;
}
