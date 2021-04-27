package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id FROM user WHERE user_name=#{userName}")
    public User selUserByUserName(User user);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id FROM user WHERE account=#{account}")
    public User selUserByAccount(User user);

    @Insert("INSERT INTO user VALUES(DEFAULT,#{account},\n" +
            "            #{userName},#{password},\n" +
            "            NULL,#{salt},NULL,NULL,NULL,#{class_id})")
    public Integer insUser(User user);

    public Integer insUserBatch(List<User> userList);

    public User selRolesByUserName(String username);

    @Select("SELECT id,account,user_name as userName,password,perms,salt,total_score,team_id,team_change_history,class_id FROM user")
    public List<User> selAllUser();
}
