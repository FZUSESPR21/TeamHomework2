package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id FROM user WHERE user_name=#{userName}")
    public User selUserByUserName(User user);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id,token_salt tokenSalt FROM user WHERE account=#{account}")
    public User selUserByAccount(User user);

    @Insert("INSERT INTO user VALUES(DEFAULT,#{account},\n" +
            "            #{userName},#{password},\n" +
            "            NULL,#{salt},NULL,NULL,NULL,#{class_id},NULL)")
    public Integer insUser(User user);

    public Integer insUserBatch(List<User> userList);

    public User selRolesByUserName(String username);

    User selRoleByUserAccount(String account);

    @Select("SELECT id,account,user_name as userName,password,perms,salt,total_score,team_id,team_change_history,class_id FROM user")
    public List<User> selAllUser();

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE id=#{id}")
    public User selUserByid(User user);

    User  selUserAndClassRoomByUserId(User user);

    @Update("UPDATE user SET token_salt=#{tokenSalt} WHERE account=#{account}")
    Integer updUserTokenSaltByAccount(User user);

    @Select("SELECT id,account,user_name as userName,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId FROM user WHERE account=#{account}")
    User selUserByAccountWhitoutPrivacy(User user);

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE team_id=#{id}")
    List<User> selUserByTeamId(Team team);
    public Integer updUserTokenSaltByAccount(User user);
}
