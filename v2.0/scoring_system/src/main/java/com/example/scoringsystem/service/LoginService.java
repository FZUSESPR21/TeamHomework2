package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.User;

import java.util.List;

public interface LoginService {
    User getUserByName(String getMapbyName);

    User getUserByAccount(String account);

    void testMapper();

    User selRolesByAccount(String account);

    User selRolesByUserName(String username);

    List<User> selAllStudentUser();

    List<User> getAllStudentUserByClassId(User user);
}
