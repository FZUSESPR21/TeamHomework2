package com.example.scoringsystem.mapper;

import com.example.scoringsystem.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AssistantMapper {

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE account like '%A%'")
    List<User> getAllAssistants();

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id FROM user WHERE user_name=#{userName}")
    User selUserByUserName(User user);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id,token_salt tokenSalt FROM user WHERE account=#{account}")
    User selUserByAccount(User user);

    @Update("UPDATE user SET user_name=#{user.userName},password=#{user.password} WHERE account=#{user.id}")
    void updateAssistant(User user);

    @Delete("DELETE FROM user WHERE account =#{user.id}")
    void delAssistant(User user);

    @Delete("DELETE FROM user_role WHERE userid=#{user.id}")
    void delAssistantRole(User user);

    @Insert("INSERT INTO user" +
            "(id,account,user_name,password,perms,salt,total_score,team_id,team_change_history,class_id,token_salt,pair_team_id)" +
            " VALUES(DEFAULT,#{account},#{userName},#{password}," +
            "NULL,#{salt},NULL,NULL,NULL,#{class_id},NULL,NULL)")
    void addAssistant(User user);

    @Insert("INSERT INTO user_role VALUES(DEFAULT,#{userid},#{roleid}")
    void addAssistantRole(User user);


}
