package com.example.scoring_system.service.impl;

import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.StaticsMapper;
import com.example.scoring_system.service.StaticsService;
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
