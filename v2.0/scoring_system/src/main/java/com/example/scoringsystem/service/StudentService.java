package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.PageRequest;
import com.example.scoringsystem.bean.ResponseData;
import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.bean.UserWithTaskAndScore;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface StudentService {
     User selSingleStudent(String id);
     List<User> selAll();
     boolean addSingleStudent(User user);
     int insStudentBatch(List<User> userList);
     boolean delStudent(String id);
     boolean updStudent1(User user);  //修改非密码和teamId
     boolean updStudent2(User user);  //修改teamId
     PageInfo<User> selByPage(PageRequest pageRequest);
     PageInfo<User> selByPageAndClassRoomId(PageRequest pageRequest,String classRoomId);
     boolean updStudent3(User user);  //修改密码
     boolean verifyPassword(User user,String oldPwd);

     ResponseData isRightStuData(User user);
     List<UserWithTaskAndScore> chart();
}
