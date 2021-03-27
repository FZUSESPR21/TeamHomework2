package com.example.forum.controller;


import com.example.forum.bean.Branchformu;
import com.example.forum.bean.Meeting;
import com.example.forum.bean.User;
import com.example.forum.bean.UserResponsBody;
import com.example.forum.service.serviceImpl.IndexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController
{
    @Autowired
    IndexServiceImpl indexSerice;

    @PostMapping("/login")
    public String login(User user, HttpSession httpSession)
    {
        User dbUser=indexSerice.getUser(user);
        System.out.println("****"+dbUser);
        if(dbUser==null)
        {
            return "redirect:/html/Login.html";
        }
        if(dbUser.getPassword().equals(user.getPassword()))
        {
            User user1=indexSerice.getUser(user);
            System.out.println("****"+user1);
            System.out.println(user1);
            httpSession.setAttribute("userName",user1.getUsername());
            httpSession.setAttribute("userId",user1.getId());
            httpSession.setAttribute("userRoleId",user1.getRoleId());

            System.out.println("roleId"+user1.getRoleId());
            if (user1.getRoleId().equals("3"))
                return "redirect:secret.html?id="+user1.getId();
            return "redirect:message_list.htmlid="+user1.getId();
        }
        else
        {
            return "redirect:/html/Login.html";
        }

    }

    @RequestMapping("/registered")
    public String registered(User user)
    {
        System.out.println("开始登录......");
        indexSerice.registered(user);

        UserResponsBody userResponsBody=new UserResponsBody();
        userResponsBody.setMsg("注册成功");
        userResponsBody.setCode("200");
        return "redirect:/html/Login.html";
    }

    @RequestMapping("/chairManList")
    @ResponseBody
    public UserResponsBody getChairManList(User user)
    {
        System.out.println("开始登录......");


        UserResponsBody userResponsBody=new UserResponsBody();
        userResponsBody.setMsg("注册成功");
        userResponsBody.setCode("200");
        userResponsBody.setData(indexSerice.showMeeting(user));
        return userResponsBody;
    }

    @RequestMapping("/getmessage")
    @ResponseBody
    public UserResponsBody getmessage(User user)
    {
        System.out.println("获取Message的用户:"+user);

        UserResponsBody userResponsBody=new UserResponsBody();
        userResponsBody.setMsg("获取成功");
        userResponsBody.setCode("200");
        userResponsBody.setData(indexSerice.showMessage(user));

        return userResponsBody;
    }

    @RequestMapping("/sendMessage")
    public String sendMessage()
    {
        return "redirect:secret.html?id=3";
    }
//    @RequestMapping("/MeetingList")
//    @ResponseBody
//    public List<Meeting> getMeetings()
//    {
//        System.out.println(indexSerice.getMeeting());
//        List<Meeting> list=indexSerice.getMeeting();
//        for(Meeting meeting:list)
//        {
//            List<Branchformu> branchformuList=new ArrayList<>();
//            String branchformuId = meeting.getBranchforumId();
//            String[] branchfromuIds = branchformuId.split(",");
//            for (int i = 0; i < branchfromuIds.length; i++)
//            {
//                Branchformu branchformu=new Branchformu();
//                branchformuList.add(indexSerice.getBranchformu(branchfromuIds[i]));
//            }
//            meeting.setBranchformuList(branchformuList);
//        }
//        return list;
//    }
}
