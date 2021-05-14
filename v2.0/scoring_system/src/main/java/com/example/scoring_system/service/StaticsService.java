package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/04
 */
public interface StaticsService {
    List<User> getStudentScores();
}
