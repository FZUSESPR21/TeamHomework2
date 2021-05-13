package com.example.scoring_system.service.impl;

import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.UserMapper;
import com.example.scoring_system.service.UserService;
import com.example.scoring_system.utils.JwtUtils;
import com.example.scoring_system.utils.SaltUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    final int SALT_SIZE = 8;
    @Autowired
    UserMapper userMapper;

    /**
     * @Description: 批量导入学生用户
     * @Param: [userList]
     * @return: java.util.List<com.example.scoring_system.bean.User>
     * @Date: 2021/4/27
     */
    @Override
    public List<User> insUserBatch(List<User> userList) {
        List<User> tmpList = new ArrayList<>();
        User user;
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            log.info("当前获得的user:" + user.toString());
            if (user.getAccount() == null || user.getUserName() == null) {
                log.info("存在空的账户名或者空名字");
                return null;
            }

            if (userMapper.selUserByAccount(user) != null) {
                log.info("账户已经存在:" + user.toString());
                //用户名已经存在
                tmpList.add(user);
            } else {
                //生成随机盐并保存
                String salt = SaltUtils.getSalt(SALT_SIZE);
                user.setSalt(salt);
                //为账户添加标识符S
                user.setAccount("s" + user.getAccount());
                //设置密码为默认密码
                user.setPassword("123456");
                //对明文密码进行md5+salt+hash散列
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
                user.setPassword(md5Hash.toHex());
            }
        }

        //无已经存在账户
        if (tmpList.size() == 0) {
            //大批量插入
            userMapper.insUserBatch(userList);
        }
        return tmpList;
    }

    /**
    * @Description: 保存user登录信息，获取token.
    * @Param: [user]
    * @return: java.lang.String
    * @Date: 2021/4/29
    */
    public String generateJwtToken(User user)
    {
        log.debug("User:"+user);
        String salt= JwtUtils.generateSalt();
        user.setTokenSalt(salt);
        //保存salt到数据库中
        userMapper.updUserTokenSaltByAccount(user);
        return JwtUtils.sign(user.getAccount(),user.getTokenSalt(),3600);
    }

    /**
    * @Description:获取上次token生成的salt指和登录用户信息。
    * @Param: [user]
    * @return: com.example.scoring_system.bean.User
    * @Date: 2021/4/30
    */
    public User getJwtTokenInfo(User user)
    {
        return userMapper.selUserByAccount(user);
    }

    /**
    * @Description: 清除toen信息
    * @Param: [user]
    * @return: java.lang.Integer
    * @Date: 2021/4/30
    */
    public Integer deleteLoginInfo(User user)
    {
        log.debug("消除token的user:"+user);
        user.setTokenSalt("");
        return userMapper.updUserTokenSaltByAccount(user);
    }
}
