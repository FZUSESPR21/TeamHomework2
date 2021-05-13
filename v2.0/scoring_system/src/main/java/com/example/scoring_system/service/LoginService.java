package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

public interface LoginService {
    public User getUserByName(String getMapbyName);
    public User getUserByAccount(String account);
    public  void testMapper();
    public User selRolesByUserName(String username);
    public List<User> selAllUser();

}
