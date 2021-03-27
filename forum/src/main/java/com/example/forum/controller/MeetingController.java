package com.example.forum.controller;


import com.example.forum.bean.Branchformu;
import com.example.forum.bean.Meeting;
import com.example.forum.bean.UserResponsBody;
import com.example.forum.bean.User;
import com.example.forum.service.serviceImpl.IndexServiceImpl;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*
[
  {
    "id": "111",
    "name": "111",
    "chairManId": "123456",
    "branchforumId": [
      "aaa",
      "bbb",
      "ccc"
    ],
    "startTime": "2000-01-01",
    "joinPaper": "654"
  },

  {
    "id": "222",
    "name": "222",
    "chairManId": "123456",
    "branchforumId": [
      "aaa",
      "bbb",
      "ccc"
    ],
    "startTime": "2000-01-01",
    "joinPaper": "654"
  },

  {
    "id": "333",
    "name": "333",
    "chairManId": "123456",
    "branchforumId": [
      "aaa",
      "bbb",
      "ccc"
    ],
    "startTime": "2000-01-01",
    "joinPaper": "654"
  }

],
 */

@Controller
public class MeetingController
{
    @Autowired
    IndexServiceImpl indexSerice;

    @RequestMapping("/MeetingList")
    @ResponseBody
    public List<Meeting> getMeetings()
    {
        System.out.println(indexSerice.getMeeting());
        List<Meeting> list=indexSerice.getMeeting();
        for(Meeting meeting:list)
        {
            List<Branchformu> branchformuList=new ArrayList<>();
            String branchformuId = meeting.getBranchforumId();
            String[] branchfromuIds = branchformuId.split(",");
            for (int i = 0; i < branchfromuIds.length; i++)
            {
                Branchformu branchformu=new Branchformu();
                branchformuList.add(indexSerice.getBranchformu(branchfromuIds[i]));
            }
            meeting.setBranchformuList(branchformuList);
        }
        return list;
    }

}
