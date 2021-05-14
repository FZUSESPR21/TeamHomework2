package com.example.scoring_system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private String id;
    private String sysTeamName;
    private String sysTeamSlogan;
    private String classRoomId;
    private List<BlogWork> blogWorkList;
    private List<User> userList;
    private ClassRoom classRoom;

    public Team(String teamName, String teamSlogan, String classId) {
        sysTeamName=teamName;
        sysTeamSlogan=teamSlogan;
        classRoomId=classId;
    }
}
