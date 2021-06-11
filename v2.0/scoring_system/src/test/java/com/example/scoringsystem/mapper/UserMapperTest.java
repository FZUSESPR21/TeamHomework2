package com.example.scoringsystem.mapper;

import com.example.scoringsystem.bean.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void insPairTeam() {
        Team team = new Team();
        team.setSysTeamName("测试测试");
        team.setClassRoomId("1");
        userMapper.insPairTeam(team);
        System.out.println(team);
    }

    @Test
    void  updUserPairTeamIdById()
    {
        userMapper.updUserPairTeamIdByAccount("175","1");
    }
}