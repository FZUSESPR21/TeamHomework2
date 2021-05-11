package com.example.scoring_system.service;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.TeamForImport;
import com.example.scoring_system.bean.User;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface TeamService {
    public PageInfo<Team> selTeamByPage(PageRequest pageRequest);
    public List<User> selAllTeamMember(Team team);
    public boolean delTeam(Team team);
    public boolean addSingleTeam(TeamForImport team);
    public Integer insTeamBatch(List<TeamForImport> teamList);
    public boolean updTeam(Team team);
}
