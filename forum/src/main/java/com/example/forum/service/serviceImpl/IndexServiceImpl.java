package com.example.forum.service.serviceImpl;


import com.example.forum.bean.Branchformu;
import com.example.forum.bean.Meeting;
import com.example.forum.bean.Message;
import com.example.forum.bean.Message2;
import com.example.forum.bean.User;
import com.example.forum.mapper.ListMapper;
import com.example.forum.mapper.MessageMapper;
import com.example.forum.mapper.UserMapper;
import com.example.forum.service.IndexSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexSerice
{

    @Autowired
    UserMapper userMapper;
    @Autowired
    ListMapper listMapper;

    @Override
    public Integer registered(User user)
    {
        System.out.println("插入的用户信息:"+user.toString());
        return userMapper.insUser(user);
    }

    @Override
    public List<Meeting> showMeeting(User user)
    {
        List<Meeting>  list=listMapper.selMeeting(user);
        List<Branchformu> branchformuList=new ArrayList<>();
        Meeting m=new Meeting();
        for (int i=0;i<list.size();i++)
        {
            System.out.println("获取的meeting"+list.get(i).toString());
            m=list.get(i);
            branchformuList=new ArrayList<>();
            List<String> stringList=new ArrayList<String>(Arrays.asList(m.getBranchforumId().split(",")));
            for (int j=0;j<stringList.size();j++)
            {
                Branchformu branchformu=new Branchformu();
                branchformu=listMapper.selBranchformmu(stringList.get(j));
                branchformuList.add(branchformu);
            }
            m.setBranchformuList(branchformuList);
            list.set(i,m);
        }
        return list;
    }

    public Message2 showMessage(User user)
    {
        Message2 message2 =new Message2();
        User user1=listMapper.selUserById(user);
        String[] strings=user1.getBranchFformus().split(",");
        List<Branchformu> branchformuList=new ArrayList<>();
        for (int i=0;strings!=null&&i<strings.length;i++)
        {
            Branchformu branchformu=listMapper.selBranchformmu(strings[i]);
            branchformu.setListMessage(Arrays.asList(branchformu.getMessage().split(",")));
            branchformuList.add(branchformu);
        }
        message2.setBranchformuList(branchformuList);
        return message2;
    }

    MessageMapper messageMapper;

    @Override
    public Integer giveMessage(Message message)
    {
        System.out.println("发布的消息:"+message.toString());
        return messageMapper.insMessage(message);
    }
}
