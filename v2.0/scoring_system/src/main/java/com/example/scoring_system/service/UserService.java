package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

public interface UserService {
    public List<User> insUserBatch(List<User> userList);
    public String generateJwtToken(User user);
    public User getJwtTokenInfo(User user);
    public Integer deleteLoginInfo(User user);
}
