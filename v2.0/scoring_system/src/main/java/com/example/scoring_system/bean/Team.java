package com.example.scoring_system.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class Team implements Serializable {
    private String id;

    @Excel(name="团队名",orderNum="1")
    private String teamName;

    @Excel(name="团队口号",orderNum="2")
    private String teamSlogan;

    @Excel(name="所属班级号",orderNum="3")
    private String classId;

    public Team(){

    }

    public Team(String b,String c,String d){
        this.teamName = b;
        this.teamSlogan = c;
        this.classId = d;
    }
}
