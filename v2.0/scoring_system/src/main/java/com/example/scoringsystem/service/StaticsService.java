package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.User;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/04
 */
public interface StaticsService {
    List<User> getStudentScores();
}
