package com.example.scoringsystem.bean;

import lombok.Data;

import java.util.List;

@Data
public class Score {
    String id;
    String score;
    String teamId;
    //个人作业使用
    String userId;
    //结对作业使用
    String user1Id;
    String user2Id;
    String taskId;
    String contributions;
    List<DetailsData> detailsDataList;
}
