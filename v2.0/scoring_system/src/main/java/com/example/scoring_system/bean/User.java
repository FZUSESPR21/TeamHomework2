package com.example.scoring_system.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
* @Description: 用户类
* @Author: 曹鑫
* @Date: 2021/4/11
*/
@Data
@AllArgsConstructor
@ExcelTarget("users")
public class User implements Serializable {
    private String id;
    @Excel(name="账户",orderNum="1")
    private String account;
    @Excel(name="姓名",orderNum="2")
    private String userName;
    @Excel(name="密码",orderNum="3")
    private String password;
    @Excel(name="权限",orderNum="4")
    private String perms;
    private Set<Role> roles;
    private String salt;
    private int class_id;
    private String tokenSalt;
    private double total_score;
    private int pair_team_id;

    public User()
    {

    }

    public User(String id, String user_name, String password, Set<Role> roles) {
        this.id = id;
        this.userName = user_name;
        this.password = password;
        this.roles = roles;
    }

    public static ArrayList<User> getUsers()
    {
        ArrayList<User> userList=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            User user=new User();
            user.setId(i+"");
            user.setUserName("小鹿"+i);
            user.setPassword("123456");
            user.setPerms("无"+i);
            userList.add(user);
        }
        return userList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTokenSalt() {
        return tokenSalt;
    }

    public void setTokenSalt(String tokenSalt) {
        this.tokenSalt = tokenSalt;
    }
}
