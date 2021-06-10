package com.example.scoringsystem.service.impl;

import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.mapper.StaticsMapper;
import com.example.scoringsystem.service.StaticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/04
 */

@Service
@Slf4j
public class StaticsServiceImpl implements StaticsService {
    @Autowired
    StaticsMapper staticsMapper;

    @Override
    public List<User> getStudentScores() {
        return staticsMapper.getAllAssistants();
    }

}
