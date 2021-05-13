package com.example.scoring_system.service;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.bean.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    List<User> insUserBatch(List<User> userList,User user);
    String generateJwtToken(User user);
    User getJwtTokenInfo(User user);
    Integer deleteLoginInfo(User user);
    User getUserByAccountWithoutPrivacy(User user);
    PageInfo<User> getUserByRoleWithStudent(PageRequest pageRequest);
    List<User> getUserListByTeamId(Team team);
    UserVO getUserAndClassRoomByUserId(User user);
}