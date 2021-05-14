package com.example.scoring_system.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ScoreItem {
    @Excel(name = "评分大项")
    String scoreItem;
}
