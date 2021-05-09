package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class TeamReplyReviewForm {
    String id;
    String teamId;
    String replyReviewForm;
    String userId;
    String score;
    String advice;
    String detailsId;
}
