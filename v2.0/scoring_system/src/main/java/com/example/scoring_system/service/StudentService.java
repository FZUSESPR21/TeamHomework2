package com.example.scoring_system.service;

import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface StudentService {
    public User selSingleStudent(String id);
    public List<User> selAll();
    public boolean addSingleStudent(User user);
    public int insStudentBatch(List<User> userList);
    public boolean delStudent(String id);
    public boolean updStudent1(User user);
    public boolean updStudent2(User user);
    public PageInfo<User> selByPage(PageRequest pageRequest);

    public ResponseData isRightStuData(User user);
}
