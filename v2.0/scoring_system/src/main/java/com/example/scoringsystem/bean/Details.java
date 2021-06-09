package com.example.scoringsystem.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Details {
    String id;

    String detailsName;
    //存入数据库分数占比
    Integer totalScoreRatio;
    String createUserId;
    String createTime;
    String taskId;
    //评分大项
    @ExcelEntity(id = "评分大项")
    ScoreItem scoreItem;
    //大项占总分比
    @Excel(name = "大项占总分比")
    String scoreRatio;
    //评分细则与评分细则占大项比
    @ExcelCollection(name = "评分细则")
    List<ScoreDetail> scoreDetail;
}
