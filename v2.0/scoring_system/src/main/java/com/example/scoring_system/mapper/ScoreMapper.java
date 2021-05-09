package com.example.scoring_system.mapper;

import com.example.scoring_system.bean.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    @Insert("INSERT INTO task(sys_id,task_name,task_content,create_user_id,create_time,begine_time,deadline,make_up_time,class_id,task_type,ratio) VALUES\n" +
            "(DEFAULT,#{taskName},#{taskContent},#{createUser.id},#{createTime},#{begineTime},#{deadline},#{makeUpTime},#{classRoom.id},#{taskType},#{ratio})")
    Integer insTask(Task task);

    @Select("SELECT sys_id id FROM task ORDER BY sys_id DESC LIMIT 1")
    Task selLastRecordInTask();

    @Select("SELECT sys_id id,task_name taskName,task_content taskContent,create_user_id creteUserId,\n" +
            "create_time createTime,begine_time begineTime,deadline,make_up_time makeUpTime,class_id classRoomId,task_type taskType,ratio \n" +
            "FROM task\n" +
            "WHERE class_id=#{classRoomId}")
    List<Task> selTaskByClassRoomId(Task task);

    @Select("SELECT sys_id id,task_name taskName,task_content taskContent,create_user_id creteUserId,\n" +
            "create_time createTime,begine_time begineTime,deadline,make_up_time makeUpTime,class_id classRoomId,task_type taskType,ratio \n" +
            "FROM task\n" +
            "WHERE sys_id=#{id}")
    Task selTaskById(Task task);

    @Insert("INSERT INTO blog_work(sys_id,blog_work_name,blog_work_content,user_id,team_id,task_id,blog_work_type,blog_url) \n" +
            "VALUES(DEFAULT,#{blogWorkName},#{blogWorkContent},#{userId},#{teamId},#{taskId},#{blogWorkType},#{blogUrl})")
    Integer insBlogWork(BlogWork blogWork);

    @Select("SELECT sys_id id,sys_team_name sysTeamName,sys_team_slogan sysTeamSlogan,class_id classRoomId FROM team WHERE sys_id=#{id}")
    Team selTeamById(Team team);

    @Select("SELECT sys_id id,blog_work_name blogWorkName,blog_work_content blogWorkContent,user_id userId,b.team_id teamId,task_id taskId,blog_url blogUrl,is_mark isMark\n" +
            " FROM blog_work b LEFT JOIN user u ON b.user_id=u.id WHERE task_id=#{id} AND u.class_id=#{classRoomId}")
    List<BlogWork> selBlogWorkByTaskIdAndClassRoomId(Task task);

    @Select("SELECT sys_id id,class_name className,grade,teacher_id teacherId FROM class")
    List<ClassRoom> selAllClass();

    @Select("SELECT sys_id id,task_name taskName,task_content taskContent,create_user_id creteUserId,\n" +
            "            create_time createTime,begine_time begineTime,deadline,make_up_time makeUpTime,class_id classRoomId,task_type taskType,ratio\n" +
            "            FROM task WHERE class_id=#{id}")
    List<Task> sellTaskByClassId(ClassRoom classRoom);

    BlogWork selAllClassRoom(BlogWork blogWork);

    BlogWork selTeamBlogWorkById(BlogWork blogWork);

    BlogWork selUserBlogWorkById(BlogWork blogWork);

    List<BlogWork> selUserBlogWorkListByUserId(User user);

    List<BlogWork> selTeamBlogWorkListByUserId(User user);

    @Select("SELECT sys_id id,blog_work_name blogWorkName,blog_work_content blogWorkContent,user_id userId,team_id teamId,task_id taskId,blog_work_type blogWorkType,blog_url blogUrl" +
            " FROM blog_work WHERE  task_id=#{taskId} AND (user_id=#{userId} OR team_id=#{teamId})")
    List<BlogWork> selBlogWorkByTaskIdAndUserIdOrTeamId(String teamId,String taskId,String userId);

    @Insert("INSERT INTO team_score VALUES(DEFAULT,NULL,#{teamId},#{taskId},#{contributions})")
    Integer insTeamScore(String teamId,String taskId,String contributions);


    @Insert("INSERT INTO user_score VALUES(DEFAULT,#{userId},#{taskId},NULL)")
    Integer insUserScore(String userId,String taskId);

    @Update("UPDATE blog_work SET is_mark=1 WHERE sys_id=#{id}")
    Integer updBlogWorkIsMarkById(BlogWork blogWork);

    @Update("UPDATE team_score SET sys_score=#{score} WHERE sys_id=#{id}")
    Integer updteamScoreById(Score score);

    @Update("UPDATE user_score SET sys_score=#{score} WHERE sys_id=#{id}")
    Integer upduserScoreById(Score score);

    @Select("SELECT sys_id id,sys_score score,team_id teamId,task_id taskId,contributions \n" +
            "FROM team_score\n" +
            "WHERE sys_id=#{id} \n")
    Score selteamScoreById(Score score);

    @Select("SELECT sys_id id,sys_score score,user_id userId,task_id taskId\n" +
            "FROM user_score\n" +
            "WHERE sys_id=#{id}")
    Score selUserScoreById(Score score);

    Integer insTeamScoreDetailsBatch(@Param("detailsDataList") List<DetailsData> detailsDataList, @Param("teamScoreId") String teamScoreId);

    @Insert("INSERT INTO team_score_details VALUES(DEFAULT,#{teamScoreId},#{detailsData.id},#{detailsData.score})")
    Integer insTeamScoreDetails(@Param("detailsData") DetailsData detailsData,@Param("teamScoreId") String teamScoreId);

    @Insert("INSERT INTO user_score_details VALUES(DEFAULT,#{userScoreId},#{detailsData.id},#{detailsData.score})")
    Integer insUserScoreDetails(@Param("detailsData") DetailsData detailsData,@Param("userScoreId") String userScoreId);

    @Update("UPDATE team_score_details SET score=#{detailsData.score} WHERE team_score_id=#{teamScoreId} AND details_id=#{detailsData.id}")
    Integer updTeamScoreDetails(@Param("detailsData") DetailsData detailsData,@Param("teamScoreId") String teamScoreId);

    @Update("UPDATE user_score_details SET score=#{detailsData.score} WHERE user_score_id=#{userScoreId} AND details_id=#{detailsData.id}")
    Integer updUserScoreDetails(@Param("detailsData") DetailsData detailsData,@Param("userScoreId") String userScoreId);

    @Select("SELECT sys_id id,score FROM team_score_details WHERE team_score_id=#{teamScoreId} AND details_id=#{details_id}")
    DetailsData selTeamScoreDetailsByTeamScoreIdAndDetailsId(@Param("teamScoreId") String teamScoreId,@Param("details_id")String detailsId);

    @Select("SELECT sys_id id,score FROM user_score_details WHERE user_score_id=#{userScoreId} AND details_id=#{details_id}")
    DetailsData selUserScoreDetailsByTeamScoreIdAndDetailsId(@Param("userScoreId") String userScoreId,@Param("details_id")String detailsId);

    @Update("UPDATE user SET total_score=#{totalScore} WHERE account=#{account}")
    Integer updUserTotalScore(User user);

    @Select("SELECT d.sys_id dId,d.details_name detailsName,task_name taskName\n" +
            "FROM details d\n" +
            "LEFT JOIN task t ON d.task_id=t.sys_id\n" +
            "WHERE details_name LIKE '答辩%' AND task_name IS NOT NULL")
    List<DetailsData> selDetailsDataWithReplyReview();

    @Insert("INSERT INTO team_reply_review_form (sys_id,team_id,reply_review_form,user_id,score,advice,details_id)\n" +
            "VALUES(DEFAULT,#{team_id},#{reply_review_form},NULL,NULL,NULL,#{detailsId})")
    Integer insTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    @Update("UPDATE team_reply_review_form SET score=#{score},reply_review_form=#{replyReviewForm}")
    Integer updTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    @Select("SELECT sys_id id,team_id teamId,reply_review_form replyReviewForm,user_id userId,score,advice,details_id detailsId\n" +
            "FROM team_reply_review_form WHERE team_id=#{teamId} AND details_id=#{detailsId}")
    List<TeamReplyReviewForm> selTeamReplyReviewFormByTeamIdandDetailsId(TeamReplyReviewForm replyReviewForm);

    @Select("SELECT sys_id id FROM team_score WHERE team_id=#{teamId}\n" +
            "AND task_id IN(SELECT task_id FROM details WHERE sys_id=#{detailsId})")
    String selTeamScoreIdByTeamIdAndDetailsId(TeamReplyReviewForm teamReplyReviewForm);
}
