package com.example.forum.service;



import com.example.forum.bean.Message;
import com.example.forum.bean.User;

public interface IndexSerice
{
    public  Integer registered(User user);
    public Integer giveMessage(Message message);
}
