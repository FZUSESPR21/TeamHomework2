package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class DetailsDataWithTeamReplyReviewFormVO {
    String id;
    String detailsName;
    String totalScoreRatio;
    String createUserId;
    String createTime;
    String taskId;
    String score;
    String taskName;
    boolean hasReplyForm;

    public DetailsDataWithTeamReplyReviewFormVO(DetailsData detailsData,Boolean hasReplyForm) {
        this.id = detailsData.id;
        this.detailsName = detailsData.detailsName;
        this.totalScoreRatio = detailsData.totalScoreRatio;
        this.createUserId = detailsData.createUserId;
        this.createTime = detailsData.createTime;
        this.taskId = detailsData.taskId;
        this.score = detailsData.score;
        this.taskName = detailsData.taskName;
        this.hasReplyForm = hasReplyForm;
    }
}
