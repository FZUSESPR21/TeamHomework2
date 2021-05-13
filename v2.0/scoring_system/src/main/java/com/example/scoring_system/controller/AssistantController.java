package com.example.scoring_system.controller;

import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.UserMapper;
import com.example.scoring_system.service.AssistantService;
import com.example.scoring_system.service.impl.AssistantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@CrossOrigin


/**
 * @Description: 显示助教信息列表
 * @Param:
 * @return:
 * @Date: 2021/5/02
 */
public class AssistantController {

    @Autowired
    AssistantService assistantService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/teacher/assistant/show")
    @ResponseBody
    public ResponseData showAllAssistant() {
        ResponseData responseData=new ResponseData();
        responseData.setMessage("成功查询到助教列表");
        responseData.setCode("204");
        responseData.setData(assistantService.showAllAssistant());
        return responseData;
    }

    @RequestMapping("/teacher/assistant/add")
    @ResponseBody
    public ResponseData addAssistant(@RequestBody  User user) {
        ResponseData responseData=new ResponseData();
        if(userMapper.selUserByAccount(user)!=null) {
            responseData.setMessage("账户名已存在");
            responseData.setCode("404");
        }
        else{
            responseData.setMessage("助教注册成功");
            responseData.setCode("204");
            assistantService.addAssistant(user);
        }
        return responseData;
    }
}
