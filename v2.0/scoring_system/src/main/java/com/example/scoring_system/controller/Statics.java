package com.example.scoring_system.controller;

import com.example.scoring_system.bean.User;
import com.example.scoring_system.service.StaticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 千帆竞争图
 * @Author:
 * @Date: 2021/05/04
 */

@Controller
@Slf4j
@CrossOrigin
public class Statics {

    @Autowired
    StaticsService staticsService;

    @RequestMapping("/teacher/statistics")
    @ResponseBody
    public List<User> getStudentInfoT(){
        return staticsService.getStudentScores();
    }

    @RequestMapping("/student/statistics")
    @ResponseBody
    public List<User> getStudentInfoS(){
        return staticsService.getStudentScores();
    }

    @RequestMapping("/assistant/statistics")
    @ResponseBody
    public List<User> getStudentInfoA(){
        return staticsService.getStudentScores();
    }
}
