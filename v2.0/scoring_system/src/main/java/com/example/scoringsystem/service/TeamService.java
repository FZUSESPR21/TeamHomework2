package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeamService {
    PageInfo<Team> selTeamByPage(PageRequest pageRequest);

    PageInfo<Team> selTeamByPageAndClassRoomId(PageRequest pageRequest,String ClassRoomId);

    PageInfo<Team> selPairTeamByPageAndClassRoomId(PageRequest pageRequest,String ClassRoomId);

    List<User> selAllTeamMember(Team team);

    List<User> selAllPairTeamMember(String userId);

    List<User> selAllPairTeamMemberByPairId(String pairId);

    boolean delTeam(Team team);

    boolean addSingleTeam(TeamForImport team);

    ResponseData insTeamBatch(List<TeamForImport> teamList);

    boolean updTeam(Team team);
}
