package com.example.forum.bean;

public class User
{
    String id;
    String username;
    String password;
    String roleId="4";
    String branchFformus;

    @Override
    public String toString()
    {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleId='" + roleId + '\'' +
                ", branchFformus='" + branchFformus + '\'' +
                '}';
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public String getBranchFformus()
    {
        return branchFformus;
    }

    public void setBranchFformus(String branchFformus)
    {
        this.branchFformus = branchFformus;
    }
}
