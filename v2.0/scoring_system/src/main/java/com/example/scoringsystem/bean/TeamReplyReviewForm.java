package com.example.scoringsystem.bean;

import lombok.Data;

@Data
public class TeamReplyReviewForm {
    String id;
    String teamId;
    String teamName;
    String replyReviewForm;
    String replyReviewFormScore;
    String userId;
    String score;
    String advice;
    String detailsId;
    String taskId;
    String reviewPeopleNum;
    //用于逻辑上，查询别的团队给自己团队的评分
    String thatTeamId;
    //0未评分 1 已评分
    Integer finnishCount;
    //用于区分班级
    String classId;
}
