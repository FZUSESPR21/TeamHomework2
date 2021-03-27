package com.example.forum.mapper;

import com.example.forum.bean.Branchformu;
import com.example.forum.bean.Meeting;
import com.example.forum.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ListMapper
{
    @Select("SELECT * FROM meeting WHERE chairManId=#{id}")
    public List<Meeting> selMeeting(User user);

    @Select("SELECT * FROM branchformu WHERE id=#{id}")
    public Branchformu selBranchformmu(String id);

    @Select("SELECT * FROM user WHERE id=#{id}")
    public User selUserById(User user);

}
