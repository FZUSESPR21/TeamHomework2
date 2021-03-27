package com.example.forum.mapper;


import com.example.forum.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper
{
//    INSERT INTO USER VALUES(DEFAULT,"曹鑫","123456","4","1,2,3");
    @Insert("INSERT INTO user VALUES(DEFAULT,#{username},#{password},#{roleId},#{branchFformus})")
    public Integer insUser(User user);
}
