package com.example.scoringsystem.mapper;

import com.example.scoringsystem.bean.Team;
import com.example.scoringsystem.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TeamMapper {
    @Select("select count(*) from team ")
    int selTeamCount();

    @Select("select sys_id as id,sys_team_name as sysTeamName,sys_team_slogan as sysTeamSlogan,class_id as classRoomId from team")
    List<Team> selAllTeam();

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history,class_id classId FROM user where team_id = #{id} and account like 's%'")
    List<User> selAllTeamMember(Team team);

    @Select(" SELECT id,account,user_name AS userName,PASSWORD,perms,salt,total_score totalScore,team_id teamId,team_change_history,class_id classId \n" +
            " FROM `user` WHERE pair_team_id = (SELECT pair_team_id FROM `user` WHERE id=#{userId}) AND account LIKE 's%'")
    List<User> selAllPairTeamMember(String userId);

    @Delete("delete from team where sys_id = #{id}")
    Integer delTeam(Team team);

    @Insert("insert into team(sys_id,sys_team_name,sys_team_slogan,class_id) value (DEFAULT,#{sysTeamName},#{sysTeamSlogan},#{classRoomId})")
    Integer addSingleTeam(Team team);

    @Update("update user set team_id = #{teamId} where account = #{id}")
    Integer updStuTeamId(String teamId, String id);

    @Select("select last_insert_id()")
    String selectLastInsertId();

    @Update("update team set sys_team_name = #{sysTeamName}, sys_team_slogan = #{sysTeamSlogan}, class_id = #{classRoomId} where sys_id = #{id}")
    Integer updTeam(Team team);

    @Update("update user set team_id = null where team_id = #{id}")
    public Integer updStuTeam(Team team);
}
