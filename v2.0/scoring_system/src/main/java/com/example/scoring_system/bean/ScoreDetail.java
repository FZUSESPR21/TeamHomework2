package com.example.scoring_system.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Description: 评分细则类与评分大项联系。
 * @Author: 曹鑫
 * @Date: 2021/4/28
 */
@Data
public class ScoreDetail {
    @Excel(name = "细则内容")
    String scoreDetailName;
    @Excel(name = "占比")
    String ratio;
}
