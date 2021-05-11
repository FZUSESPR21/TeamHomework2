package com.example.scoring_system.service.impl;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.TeamForImport;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.TeamMapper;
import com.example.scoring_system.service.TeamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    final int PAGE_NUMBER = 5;

    @Autowired
    TeamMapper teamMapper;

    @Override
    public PageInfo<Team> selTeamByPage(PageRequest pageRequest){
        int pageNum=pageRequest.getPageNum();
        int pageSize=pageRequest.getPageSize();
        log.error(String.valueOf(pageNum));
        log.error(String.valueOf(pageSize));
        PageHelper.startPage(pageNum,pageSize);
        List<Team> teamList= teamMapper.selAllTeam();
        return new PageInfo<Team>(teamList);
    }

    @Override
    public List<User> selAllTeamMember(Team team){
        List<User> studentList = teamMapper.selAllTeamMember(team);
        for (User student : studentList){
            String newAccount = student.getAccount().substring(1,student.getAccount().length());
            student.setAccount(newAccount);
        }

        return studentList;
    }

    @Override
    public boolean delTeam(Team team){
        Integer result = teamMapper.delTeam(team);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public Integer insTeamBatch(List<TeamForImport> teamList) {
        Integer count = 0;
        Integer result;
        for (int i = 0;i < teamList.size();i ++){
            log.info(teamList.get(i).toString());

            Team team = new Team(teamList.get(i).getTeamName(),teamList.get(i).getTeamSlogan(),teamList.get(i).getClassId());
            result = teamMapper.addSingleTeam(team);
            if (result == 1) count++;

            String teamId = teamMapper.selectLastInsertId();
            log.error("最近插入的team的id是"+teamId);
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent1());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent2());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent3());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent4());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent5());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent6());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent7());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent8());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent9());
            teamMapper.updStuTeamId(teamId,"s"+teamList.get(i).getStudent10());
        }
        return count;
    }

    @Override
    public boolean addSingleTeam(TeamForImport team){
        Team team1 = new Team(team.getTeamName(),team.getTeamSlogan(),team.getClassId());
        Integer result = teamMapper.addSingleTeam(team1);

        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent1());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent2());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent3());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent4());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent5());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent6());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent7());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent8());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent9());
        teamMapper.updStuTeamId(team.getId(),"s"+team.getStudent10());

        if (result == 1)
            return true;
        return false;
    }

    @Override
    public boolean updTeam(Team team) {
        Integer result = teamMapper.updTeam(team);
        if (result == 1){
            return true;
        }
        return false;
    }
}