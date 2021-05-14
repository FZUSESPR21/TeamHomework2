package com.example.scoring_system.service.impl;


import com.example.scoring_system.bean.PageRequest;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.StudentMapper;
import com.example.scoring_system.service.StudentService;
import com.example.scoring_system.utils.SaltUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    final int PAGE_NUMBER = 3;
    final int SALT_SIZE = 8;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public PageInfo<User> selByPage(PageRequest pageRequest) {
        int pageNum=pageRequest.getPageNum();
        int pageSize=pageRequest.getPageSize();
        log.error(String.valueOf(pageNum));
        log.error(String.valueOf(pageSize));
        PageHelper.startPage(pageNum,pageSize);
        List<User> studentList= this.selAll();
        return new PageInfo<User>(studentList);
    }

    @Override
    public User selSingleStudent(String id) {
        User user = studentMapper.selSingleStudent(id);
        if (user != null){
            String newAccount = user.getAccount().substring(1,user.getAccount().length());
            user.setAccount(newAccount);
        }
        return user;
    }

    @Override
    public List<User> selAll() {
        List<User> studentList = studentMapper.selAll();
        if (studentList != null){
            for (User user : studentList){
                String newAccount = user.getAccount().substring(1,user.getAccount().length());
                user.setAccount(newAccount);
            }
        }
        return studentList;
    }

    @Override
    public boolean addSingleStudent(User user) {
        Integer result;
        log.info("当前获得的user:" + user.toString());

            //生成随机盐并保存
            String salt = SaltUtils.getSalt(SALT_SIZE);
            user.setSalt(salt);
            //设置密码为默认密码
            user.setPassword("123456");
            //对明文密码进行md5+salt+hash散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
            user.setPassword(md5Hash.toHex());

        log.error(user.getUserName()+user.getAccount());
        result = studentMapper.addSingleStudent(user);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public boolean delStudent(String id) {
        Integer result;
        result = studentMapper.delStudent(id);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public int insStudentBatch(List<User> userList) {
        Integer result;
        int size = 0;
        ResponseData responseData;
        User user;
        for (int i=0;i<userList.size();i++){
            user = userList.get(i);
            responseData = this.isRightStuData(user);
            log.info(responseData.toString());
            if (responseData.getCode().equals("200")){
                String salt = SaltUtils.getSalt(SALT_SIZE);
                user.setSalt(salt);
                //设置密码为默认密码
                user.setPassword("123456");
                //对明文密码进行md5+salt+hash散列
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
                user.setPassword(md5Hash.toHex());
                result = studentMapper.addSingleStudent(user);
                if (result ==1){
                    size ++;
                }
            }
        }
        return size;
    }

    @Override
    public ResponseData isRightStuData(User user) {
        if (user == null)
            return new ResponseData("传入数据为空","1001","[]");
        if (user.getAccount()==null || user.getUserName()==null)
            return new ResponseData("没有传入学生相关账户信息","1002","[]");
        user.setAccount("S"+user.getAccount());        //在判断的时候就在学生账号中加入了
        if (studentMapper.selStuByAccount(user.getAccount()) != null){
            String account = user.getAccount().substring(1,user.getAccount().length());
            return new ResponseData("已经存在学生账户："+account,"1003","[]");}
        return new ResponseData("学生账户无误：","200","[]");
    }

    @Override
    public boolean updStudent1(User user) {
        log.info(user.toString());
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getSalt(), 1024);
        user.setPassword(md5Hash.toHex());

        Integer result = studentMapper.updStudent1(user);
        if (result == 1)
            return true;
        return false;
    }

    @Override
    public boolean updStudent2(User user) {
        String originalTeamId = studentMapper.selTeamId(user);
        if (originalTeamId.equals(user.getTeamId())){
            return false;
        }
        String originalTeamName = studentMapper.selTeamName(originalTeamId);
        String tempTeamName = studentMapper.selTeamName(user.getTeamId());
        String change = studentMapper.selTeamChangeHistory(user.getId());
        if (change == null) {
            change = String.format("%s(teamID为%s)->%s(teamID为%s)", originalTeamName, originalTeamId, tempTeamName, user.getTeamId());
        }
        else{
            change += ",";
            change += String.format("%s(teamID为%s)->%s(teamID为%s)", originalTeamName, originalTeamId, tempTeamName, user.getTeamId());
        }
        log.error(change);

        Integer result = studentMapper.updStudent2(user,change);
        if (result == 1)
            return true;
        return false;
    }
}
