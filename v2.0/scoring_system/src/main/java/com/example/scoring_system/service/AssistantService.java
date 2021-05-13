package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

public interface AssistantService {
    public List<User> showAllAssistant();
    public void addAssistant(User user);
    public void updateAssistant();
    public void delAssistant();
}
