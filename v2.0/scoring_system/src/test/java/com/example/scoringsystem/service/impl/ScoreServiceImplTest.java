package com.example.scoringsystem.service.impl;

import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ScoreServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void importScoreDetais() {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setAccount("222001111");
        user.setUserName("啛啛喳喳");
        userList.add(user);
        user.setClassId("1");
        userService.insUserBatch(userList,user);
    }
}