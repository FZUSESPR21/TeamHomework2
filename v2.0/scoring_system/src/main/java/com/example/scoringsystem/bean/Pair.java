package com.example.scoringsystem.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("pairs")
public class Pair {
    //结对队伍id
    private String id;
    @Excel(name="成员1学号",orderNum = "1")
    private String account1;
    @Excel(name="成员2学号",orderNum = "2")
    private String account2;
}
