package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/04
 */

@Repository
@Mapper
public interface StaticsMapper {
    @Select("SELECT id,account,user_name userName,PASSWORD,perms,salt,total_score,team_id teamId,team_change_history teamChangeHistory,class_id classId " +
            "FROM user WHERE account like '%S%'")
    List<User> getAllAssistants();
}
