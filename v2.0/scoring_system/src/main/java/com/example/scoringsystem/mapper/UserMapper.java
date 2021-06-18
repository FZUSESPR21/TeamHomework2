package com.example.scoringsystem.mapper;

import com.example.scoringsystem.bean.ClassRoom;
import com.example.scoringsystem.bean.Team;
import com.example.scoringsystem.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classRoomId,token_salt tokenSalt" +
            " FROM user WHERE user_name=#{userName}")
    User selUserByUserName(User user);

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classRoomId,token_salt tokenSalt\n" +
            "FROM user WHERE account=#{account}")
    User selUserByAccount(User user);

    @Select("SELECT \n" +
            "u.id,u.account,u.user_name userName,u.PASSWORD,u.perms,u.salt,u.total_score totalScore,u.team_id teamId,u.team_change_history teamChangeHistory,u.class_id classRoomId,\n" +
            "u.token_salt tokenSalt\n" +
            "FROM USER u LEFT JOIN user_role ur ON u.id=ur.userid LEFT JOIN role r ON ur.roleid=r.id WHERE r.rolename='student'")
    List<User> selUserByRoleWithStudent();

    @Insert("INSERT INTO user VALUES(DEFAULT,#{account},\n" +
            "            #{userName},#{password},\n" +
            "            NULL,#{salt},NULL,NULL,NULL,#{class_id},NULL)")
    Integer insUser(User user);

    @Insert("INSERT INTO user_role(id,userId,roleid) SELECT NULL,user.id,2 FROM user WHERE account=#{account}")
    Integer insStudentRole(User user);

    Integer insUserBatch(List<User> userList);

    User selRolesByUserName(String username);

    User selRoleByUserAccount(String account);

    @Select("SELECT id,account,user_name as userName,password,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE account LIKE 's%' ")
    List<User> selAllStudentUser();

    @Select("SELECT id,account,user_name as userName,password,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId FROM user " +
            "where class_id=#{classId} AND account LIKE 's%' ")
    List<User> selAllStudentUserByClassId(User user);

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId,pair_team_id pairTeamId " +
            "FROM user WHERE id=#{id}")
    User selUserById(User user);

    User selUserAndClassRoomByUserId(User user);

    @Update("UPDATE user SET token_salt=#{tokenSalt} WHERE account=#{account}")
    Integer updUserTokenSaltByAccount(User user);

    @Select("SELECT id,account,user_name as userName,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId FROM user WHERE account=#{account}")
    User selUserByAccountWhitoutPrivacy(User user);

    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE team_id=#{id}")
    List<User> selUserByTeamId(Team team);

    @Insert("INSERT INTO class(sys_id,class_name,grade,teacher_id) VALUES(DEFAULT,#{className},#{grade},#{teacherId})")
    Integer insClassRoom(String className,String grade,String teacherId);

    @Select("SELECT sys_id id,class_name className,grade,teacher_id teacherId \n" +
            "FROM class\n" +
            "WHERE class_name=#{className}")
    List<ClassRoom> selClassRoomByClassName(String className);

    Integer insPairTeam(Team team);

    @Update("UPDATE user SET pair_team_id=#{pairTeamId} WHERE account=#{account}")
    Integer updUserPairTeamIdByAccount(String account, String pairTeamId);

    @Select("SELECT * FROM team WHERE  sys_team_name=#{teamName}")
    List<Team> selTeamByTeamName(String teamName);
}
