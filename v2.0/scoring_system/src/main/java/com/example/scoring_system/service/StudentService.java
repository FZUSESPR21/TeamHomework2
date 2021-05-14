package com.example.scoring_system.service;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface StudentService {
    User selSingleStudent(String id);

    List<User> selAll();

    boolean addSingleStudent(User user);

    int insStudentBatch(List<User> userList);

    boolean delStudent(String id);

    boolean updStudent1(User user);

    boolean updStudent2(User user);

    PageInfo<User> selByPage(PageRequest pageRequest);

    ResponseData isRightStuData(User user);
}
