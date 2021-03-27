package com.example.forum.service;



import com.example.forum.bean.Meeting;
import com.example.forum.bean.User;

import java.util.List;

public interface IndexSerice
{
    public  Integer registered(User user);
    public List<Meeting> showMeeting(User user);
    public User getUser(User user);
}

