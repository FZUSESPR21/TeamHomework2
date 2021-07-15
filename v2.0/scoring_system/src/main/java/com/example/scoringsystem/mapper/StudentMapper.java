package com.example.scoringsystem.mapper;

import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.bean.UserWithTaskAndScore;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentMapper {

    @Delete("delete from user where account = #{account}")
    public Integer delStudentByAccount(String account);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score as totalScore,team_id as teamId,class_id as classId FROM user WHERE id=#{id} and account like 'S%'")
    public User selSingleStudent(String id);

   @Insert("INSERT INTO user(id,account,user_name,password,perms,salt,total_score,team_id,team_change_history,class_id,token_salt,pair_team_id) VALUES(DEFAULT,#{account},#{userName},#{password},null,#{salt},0,#{teamId},null,#{classId},null,null)")
    public Integer addSingleStudent(User user);

    @Delete("delete from user where id = #{id} and account like 'S%'")
    public Integer delStudent(String id);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score as totalScore,team_id as teamId,class_id as classId FROM user where account like 'S%'")
    public List<User> selAll();

 @Select(" SELECT id,account,user_name AS userName,PASSWORD,perms,salt,total_score AS totalScore,team_id AS teamId,class_id AS classId FROM user \n" +
         " WHERE account LIKE 'S%' AND class_id=#{classRoomId}")
 public List<User> selUserByClassRoomId(String classRoomId);

    @Select("SELECT u.id,account,user_name as userName,PASSWORD,perms,salt,total_score as totalScore,team_id as teamId,class_id as classId FROM user u\n" +
            "        left join user_role ur on ur.userid = u.id\n" +
            "        where ur.roleid = 2 limit #{pageNo},#{pageSize}")
    public List<User> selByPage(int pageNo,int pageSize);

    @Select("select count(*) from user u " +
            "left join user_role ur on u.id = ur.userid" +
            " where ur.roleid = 2")
    public int selStudentCount();

    @Update("update user set password = #{password}, salt = #{salt} where id = #{id}")
    public Integer updStudent3(User user);

    @Update("update user set user_name = #{userName}, total_score = #{totalScore}, class_id = #{classId},perms = #{perms} where id = #{id}")
    public Integer updStudent1(User user);

    @Update("update user set team_id = #{user.teamId}, team_change_history = #{change} where id = #{user.id}")
    public Integer updStudent2(User user,String change);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score as totalScore,team_id as teamId,class_id as classId FROM user where account = #{account}")
    public User selStuByAccount(String account);

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score as totalScore,team_id as teamId,class_id as classId FROM user where id = #{id}")
    public User selStuById(String id);

    @Select("select team_id from user where id = #{id}")
    public String selTeamId(User user);

    @Select("select sys_team_name from team where sys_id = #{id}")
    public String selTeamName(String id);

    @Select("select team_change_history from user where id = #{id}")
    public String selTeamChangeHistory(String id);


    public List<UserWithTaskAndScore> getchart();
}
