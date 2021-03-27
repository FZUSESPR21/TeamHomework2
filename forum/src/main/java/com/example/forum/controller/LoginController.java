package com.example.forum.controller;


import com.example.forum.bean.UserResponsBody;
import com.example.forum.bean.User;
import com.example.forum.service.serviceImpl.IndexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController
{
    @Autowired
    IndexServiceImpl indexSerice;

    @RequestMapping("/Login")
    @ResponseBody
    public User login(User user, HttpSession httpSession)
    {
        User dbUser=indexSerice.getUser(user);
        if(dbUser==null)
        {
            return new User();
        }
        if(dbUser.getPassword().equals(user.getPassword()))
        {
            User user1=indexSerice.getUser(user);
            System.out.println(user1);
            httpSession.setAttribute("userName",user1.getUsername());
            httpSession.setAttribute("userId",user1.getId());
            httpSession.setAttribute("userRoleId",user1.getRoleId());
            return user1;
        }
        else
        {
            return new User();
        }

    }



}
