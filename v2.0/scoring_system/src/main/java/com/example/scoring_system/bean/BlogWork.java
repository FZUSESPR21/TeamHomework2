package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class BlogWork {
    String id;
    String blogWorkName;
    String blogWorkContent;
    String userId;
    String teamId;
    String taskId;
    String blogWorkType;
    String blogUrl;
    Team team;
    Task task;
    User user;
    Score score;
}
