package com.example.forum.service.serviceImpl;


import com.example.forum.bean.Message;
import com.example.forum.bean.User;
import com.example.forum.mapper.MessageMapper;
import com.example.forum.mapper.UserMapper;
import com.example.forum.service.IndexSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexSerice
{

    @Autowired
    UserMapper userMapper;
    MessageMapper messageMapper;

    @Override
    public Integer registered(User user)
    {
        System.out.println("插入的用户信息:"+user.toString());
        return userMapper.insUser(user);
    }
    @Override
    public Integer giveMessage(Message message)
    {
        System.out.println("发布的消息:"+message.toString());
        return messageMapper.insMessage(message);
    }
}
