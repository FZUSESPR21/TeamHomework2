package com.example.scoring_system.service;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.TeamForImport;
import com.example.scoring_system.bean.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeamService {
    PageInfo<Team> selTeamByPage(PageRequest pageRequest);

    List<User> selAllTeamMember(Team team);

    boolean delTeam(Team team);

    boolean addSingleTeam(TeamForImport team);

    Integer insTeamBatch(List<TeamForImport> teamList);

    boolean updTeam(Team team);
}
