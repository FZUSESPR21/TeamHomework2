package com.example.forum.controller;

import com.example.forum.bean.Message;
import com.example.forum.bean.MessageResponsBody;
import com.example.forum.service.serviceImpl.IndexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class MessageController {
    @Autowired
    IndexServiceImpl indexSerice;

    @RequestMapping("/giveMessage")
    @ResponseBody
    public MessageResponsBody giveMessage(Message message)
    {
        System.out.println("开始发布......");
        indexSerice.giveMessage(message);

        MessageResponsBody messageResponsBody=new MessageResponsBody();
        messageResponsBody.setMsg("发布成功");
        messageResponsBody.setCode("200");
        return messageResponsBody;
    }
}
