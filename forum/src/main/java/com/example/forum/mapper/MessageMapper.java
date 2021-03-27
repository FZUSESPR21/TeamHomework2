package com.example.forum.mapper;

import com.example.forum.bean.Message;
import org.apache.ibatis.annotations.Insert;

public interface MessageMapper {
    /*@Select("select * from useer where username=#{username}")
    public User sleUser(User user);*/

    @Insert("INSERT INTO message VALUES(DEFAULT,#{content},#{writerId},#{time})")
    public Integer insMessage(Message message);
}
