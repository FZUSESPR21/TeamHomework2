package com.example.scoring_system.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    String id;
    String taskName;
    String taskContent;
    User createUser=new User();
    String createTime;
    String begineTime;
    String deadline;
    String makeUpTime;
    List<Details> detailsList=new ArrayList<>();
    ClassRoom classRoom=new ClassRoom();
    String classRoomId;
    String creteUserId;
}
