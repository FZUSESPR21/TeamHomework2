package com.example.scoringsystem.service.impl;


import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.mapper.AssistantMapper;
import com.example.scoringsystem.service.AssistantService;
import com.example.scoringsystem.utils.SaltUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j


public class AssistantServiceImpl implements AssistantService {

    int SALT_SIZE = 8;

    @Autowired
    AssistantMapper assistantMapper;


    /**
     * @Description: 从数据库中取得所有助教的信息并返回
     * @Param: null
     * @return: List<User>
     * @Date: 2021/5/2
     */
    @Override
    public List<User> showAllAssistant() {
        return assistantMapper.getAllAssistants();
    }


    /**
     * @Description: 新增助教
     * @Param: null
     * @return:
     * @Date: 2021/5/2
     */
    @Override
    public void addAssistant(User user) {
        User tempuser = user;
        if (assistantMapper.selUserByAccount(user) != null) {
            log.info("账户已经存在:" + user.toString());
            //账户名已经存在
            return;
        } else {
            //生成随机盐并保存
            String salt = SaltUtils.getSalt(SALT_SIZE);
            tempuser.setSalt(salt);
            //为账户添加标识符A
            tempuser.setAccount("A" + user.getAccount());
            //设置密码为默认密码
            tempuser.setPassword(user.getPassword());
            //对明文密码进行md5+salt+hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
            tempuser.setPassword(md5Hash.toHex());
        }
        log.info(tempuser.toString());
        assistantMapper.addAssistant(tempuser);
    }

    @Override
    public void updateAssistant() {

    }

    @Override
    public void delAssistant() {

    }
}
