package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreMapper {
    /** 
    * @Description: 批量导入评分细则
    * @Param: [detailsList] 
    * @return: java.lang.Integer 
    * @Date: 2021/4/29 
    */
   Integer insDetailsBatch(List<DetailsData> detailsList);

    /**
    * @Description: 导入作业信息
    * @Param: [task]
    * @return: java.lang.Integer
    * @Date: 2021/4/29
    */
    @Insert("INSERT INTO task(sys_id,task_name,task_content,create_user_id,create_time,begine_time,deadline,make_up_time,class_id) VALUES\n" +
            "(DEFAULT,#{taskName},#{taskContent},#{createUser.id},#{createTime},#{begineTime},#{deadline},#{makeUpTime},#{classRoom.id})")
    Integer insTask(Task task);

    @Select("SELECT sys_id id FROM task ORDER BY sys_id DESC LIMIT 1")
    Task selLastRecordInTask();

    @Select("SELECT sys_id,task_name,task_content,create_user_id,create_time,begine_time,deadline,make_up_time,class_id from details")
    public List<Details> selDetails();

    @Delete("DELETE FROM details where sys_id=#{sys_id}")
    public void delDetails(String sys_id);
}
