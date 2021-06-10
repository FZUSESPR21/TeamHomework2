package com.example.scoringsystem.bean;

import lombok.Data;

@Data
public class ClassRoom {
    Integer id;
    String className;
    String grade;
    String teacherId;
    User user;
}