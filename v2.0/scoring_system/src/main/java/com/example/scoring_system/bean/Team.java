package com.example.scoring_system.bean;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String id;
    private String sysTeamName;
    private String sysTeamSlogan;
    private String classRoomId;
    private ClassRoom classRoom;
    private List<BlogWork> blogWorkList;
    private List<User> userList;


}
