package com.example.scoringsystem.service.impl;

import com.example.scoringsystem.bean.*;
import com.example.scoringsystem.mapper.TeamMapper;
import com.example.scoringsystem.mapper.UserMapper;
import com.example.scoringsystem.service.UserService;
import com.example.scoringsystem.utils.JwtUtils;
import com.example.scoringsystem.utils.SaltUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/** 
* @Description: 用户相关类
* @Author: 曹鑫
* @Date: 2021/6/10 
*/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    final int SALT_SIZE = 8;
    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    /**
     * @Description: 批量导入学生用户
     * @Param: [userList]
     * @return: java.util.List<com.example.scoring_system.bean.User>
     * @Date: 2021/4/27
     */
    @Override
    public List<User> insUserBatch(List<User> userList, User u) {
        List<User> tmpList = new ArrayList<>();
        User user;
        for (int i = 0; i < userList.size(); i++) {
            user = userList.get(i);
            log.info("当前获得的user:" + user.toString());
            if (user.getAccount() == null || user.getUserName() == null) {
                log.info("存在空的账户名或者空名字");
                return null;
            }
            //为账户添加标识符S
            user.setAccount("s" + user.getAccount());
            if (userMapper.selUserByAccount(user) != null) {
                log.info("账户已经存在:" + user.toString());
                //用户名已经存在
                tmpList.add(user);
            } else {
                //生成随机盐并保存
                String salt = SaltUtils.getSalt(SALT_SIZE);
                user.setSalt(salt);
                //设置密码为默认密码
                user.setPassword("123456");
                //对明文密码进行md5+salt+hash散列
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
                user.setPassword(md5Hash.toHex());
                log.info("&&*&*&*&*" + u.getClassId());
                user.setClassId(u.getClassId());
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
    * @Description: 保存结对队友
    * @Author: 曹鑫
    * @Date: 2021/6/11 
    */
    @Override
    @Transactional
    public Boolean savePair(List<Pair> pairList,String classRoomId) {
        for (int i=0;i<pairList.size();i++)
        {
            Pair pair=pairList.get(i);
            if (pair.getAccount1()!=null&&pair.getAccount2()!=null)
            {
            String teamName=pair.getAccount1()+"&&&@@@"+pair.getAccount2();
            Team team=new Team();
            team.setSysTeamName(teamName);
            team.setClassRoomId(classRoomId);
            List<Team> teams=userMapper.selTeamByTeamName(teamName);
            if (teams!=null&&teams.size()>1)
            {
                team.setId(teams.get(0).getId());
            }
            else
            {
                userMapper.insPairTeam(team);
            }
            userMapper.updUserPairTeamIdByAccount("s"+pair.getAccount1(),team.getId());
            userMapper.updUserPairTeamIdByAccount("s"+pair.getAccount2(),team.getId());
        }
        }
        return true;
    }

    /**
     * @Description: 保存user登录信息，获取token.
     * @Param: [user]
     * @return: java.lang.String
     * @Date: 2021/4/29
     */
    @Override
    public String generateJwtToken(User user) {
        log.debug("User:" + user);
        String salt = JwtUtils.generateSalt();
        user.setTokenSalt(salt);
        //保存salt到数据库中
        userMapper.updUserTokenSaltByAccount(user);
        return JwtUtils.sign(user.getAccount(), user.getTokenSalt(), 3600);
    }

    /**
     * @Description:获取上次token生成的salt指和登录用户信息。
     * @Param: [user]
     * @return: com.example.scoring_system.bean.User
     * @Date: 2021/4/30
     */
    @Override
    public User getJwtTokenInfo(User user) {
        return userMapper.selUserByAccount(user);
    }

    /**
     * @Description: 清除toen信息
     * @Param: [user]
     * @return: java.lang.Integer
     * @Date: 2021/4/30
     */
    @Override
    public Integer deleteLoginInfo(User user) {
        log.debug("消除token的user:" + user);
        user.setTokenSalt("");
        return userMapper.updUserTokenSaltByAccount(user);
    }

    @Override
    public User getUserByAccountWithoutPrivacy(User user) {
        User user1 = userMapper.selUserByAccountWhitoutPrivacy(user);
        log.info(user1.toString());
        User user2 = userMapper.selRoleByUserAccount(user.getAccount());
        log.info(user2.toString());
        user1.setRoles(user2.getRoles());
        return user1;
    }

    /**
     * @Description: 获取所有学生用户的信息
     * @Param: []
     * @return: java.util.List<com.example.scoring_system.bean.User>
     * @Date: 2021/5/7
     */
    @Override
    public PageInfo<User> getUserByRoleWithStudent(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        return new PageInfo<>(userMapper.selUserByRoleWithStudent());
    }

    @Override
    public List<User> getUserListByTeamId(Team team) {
        List<User> userList = userMapper.selUserByTeamId(team);
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).setPassword("");
        }

        return userList;
    }

    @Override
    public UserVO getUserAndClassRoomByUserId(User user) {
        log.info("查询的user" + user.getId());
        User user1 = userMapper.selUserAndClassRoomByUserId(user);
        if (user1 == null) {
            return new UserVO();
        }
        return new UserVO(user1.getId(), user1.getAccount(), user1.getUserName(), user1.getClassRoom().getClassName());
    }

    @Override
    public ResponseData insClassRomm(ClassRoom classRoom) {
        if (userMapper.selClassRoomByClassName(classRoom.getClassName()).size()>0) {
            return new ResponseData("插入失败，班级名已经存在","1052","[]");
        }
        if (userMapper.insClassRoom(classRoom.getClassName(),classRoom.getGrade(), classRoom.getTeacherId())>0) {
            return new ResponseData("插入成功","200","[]");
        }
        return new ResponseData("插入失败","1051","[]");
    }
}
