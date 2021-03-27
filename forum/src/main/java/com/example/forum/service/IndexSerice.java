package com.example.forum.service;



import com.example.forum.bean.Meeting;
import com.example.forum.bean.Message;
import com.example.forum.bean.User;
import com.example.forum.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IndexSerice
{
    public  Integer registered(User user);
    public Integer giveMessage(Message message);
    public List<Meeting> showMeeting(User user);
}
