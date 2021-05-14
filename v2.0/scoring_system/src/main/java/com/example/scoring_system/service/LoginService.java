package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

public interface LoginService {
    User getUserByName(String getMapbyName);

    User getUserByAccount(String account);

    void testMapper();

    User selRolesByUserName(String username);

    List<User> selAllUser();

}
