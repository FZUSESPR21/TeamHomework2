package com.example.scoring_system.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private String id;
    private String taskName;
    private String taskContent;
    private String createUserId;
    private User createUser=new User();
    private String createTime;
    private String begineTime;
    private String deadline;
    private String makeUpTime;
    private List<Details> detailsList=new ArrayList<>();
    private ClassRoom classRoom=new ClassRoom();
    private String classRoomId;
    private String creteUserId;
    private String taskType;
    private List<DetailsData> detailsData;
    private String ratio;
}
