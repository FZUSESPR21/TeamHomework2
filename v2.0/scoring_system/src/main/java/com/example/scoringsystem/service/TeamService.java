package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeamService {
    PageInfo<Team> selTeamByPage(PageRequest pageRequest);

    List<User> selAllTeamMember(Team team);

    boolean delTeam(Team team);

    boolean addSingleTeam(TeamForImport team);

    ResponseData insTeamBatch(List<TeamForImport> teamList);

    boolean updTeam(Team team);
}
