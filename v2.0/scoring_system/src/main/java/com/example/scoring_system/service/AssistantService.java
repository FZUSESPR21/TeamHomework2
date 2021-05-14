package com.example.scoring_system.service;

import com.example.scoring_system.bean.User;

import java.util.List;

public interface AssistantService {
    List<User> showAllAssistant();

    void addAssistant(User user);

    void updateAssistant();

    void delAssistant();
}
