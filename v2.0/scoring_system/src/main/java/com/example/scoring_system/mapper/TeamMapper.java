package com.example.scoring_system.mapper;
import com.example.scoring_system.bean.Team;
import com.example.scoring_system.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TeamMapper {
    @Select("select count(*) from team ")
    public int selTeamCount();

    @Select("select sys_id as id,sys_team_name as sysTeamName,sys_team_slogan as sysTeamSlogan,class_id as classRoomId from team")
    public List<Team> selAllTeam();

    @Select("SELECT id,account,user_name as userName,PASSWORD,perms,salt,total_score,team_id,team_change_history,class_id FROM user where team_id = #{id} and account like 's%'")
    public List<User> selAllTeamMember(Team team);

    @Delete("delete from team where sys_id = #{id}")
    public Integer delTeam(Team team);

    @Insert("insert into team(sys_id,sys_team_name,sys_team_slogan,class_id) value (DEFAULT,#{sysTeamName},#{sysTeamSlogan},#{classRoomId})")
    public Integer addSingleTeam(Team team);

    @Update("update user set team_id = #{teamId} where account = #{id}")
    public Integer updStuTeamId(String teamId,String id);

    @Select("select last_insert_id()")
    public String selectLastInsertId();

    @Update("update team set sys_team_name = #{sysTeamName}, sys_team_slogan = #{sysTeamSlogan}, class_id = #{classRoomId} where sys_id = #{id}")
    public Integer updTeam(Team team);

}
