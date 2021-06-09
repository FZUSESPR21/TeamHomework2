package com.example.scoringsystem.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeamForImport implements Serializable {
    private String id;

    @Excel(name = "团队名", orderNum = "1")
    private String teamName;

    @Excel(name = "团队口号", orderNum = "2")
    private String teamSlogan;

    @Excel(name = "所属班级号", orderNum = "3")
    private String classId;

    @Excel(name = "学生账户1")
    private String student1;

    @Excel(name = "学生账户2")
    private String student2;

    @Excel(name = "学生账户3")
    private String student3;

    @Excel(name = "学生账户4")
    private String student4;

    @Excel(name = "学生账户5")
    private String student5;

    @Excel(name = "学生账户6")
    private String student6;

    @Excel(name = "学生账户7")
    private String student7;

    @Excel(name = "学生账户8")
    private String student8;

    @Excel(name = "学生账户9")
    private String student9;

    @Excel(name = "学生账户10")
    private String student10;
}
