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
    List<Task> selTaskByClassId(ClassRoom classRoom);

    @Select("        SELECT sys_id id,task_name taskName,task_content taskContent,create_user_id creteUserId,\n" +
            "        create_time createTime,begine_time begineTime,deadline,make_up_time makeUpTime,class_id classRoomId,task_type taskType,ratio\n" +
            "        FROM task WHERE class_id=1 AND task_type=#{taskType}")
    List<Task> selTaskByClassIdAndType(Task task);

    BlogWork selAllClassRoom(BlogWork blogWork);

    BlogWork selTeamBlogWorkById(BlogWork blogWork);

    BlogWork selUserBlogWorkById(BlogWork blogWork);

    List<BlogWork> selUserBlogWorkListByClassIdAndTaskId(Task task);

    List<BlogWork> selTeamBlogWorkListByClassIdAndTaskId(Task task);

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

    @Select("SELECT d.sys_id id,d.details_name detailsName,task_name taskName,d.task_id taskId\n" +
            "            FROM details d\n" +
            "            LEFT JOIN task t ON d.task_id=t.sys_id\n" +
            "            WHERE details_name LIKE '答辩%' AND task_name IS NOT NULL\n")
    List<DetailsData> selDetailsDataWithReplyReview();

    @Select("SELECT sys_id id,details_name detailsName,score_ratio totalScoreRatio,create_user_id createUserId,create_time createTime,task_id taskId FROM details WHERE task_id=#{id}")
    List<DetailsData> selDetailsDataByTaskId(Task task);

    @Insert("INSERT INTO team_reply_review_form (sys_id,team_id,reply_review_form,user_id,score,advice,details_id)\n" +
            "VALUES(DEFAULT,#{teamId},#{replyReviewForm},#{userId},#{score},NULL,#{detailsId})")
    Integer insTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    @Update("UPDATE team_reply_review_form SET score=#{score},reply_review_form=#{replyReviewForm},user_id=#{userId}" +
            "WHERE team_id=#{teamId} AND details_id=#{detailsId} AND user_id=#{userId}")
    Integer updTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    @Select("SELECT sys_id id,team_id teamId,reply_review_form replyReviewForm,user_id userId,score,advice,details_id detailsId\n" +
            "FROM team_reply_review_form WHERE team_id=#{teamId} AND details_id=#{detailsId} AND user_id=#{userId}")
    List<TeamReplyReviewForm> selTeamReplyReviewFormByTeamIdAndDetailsIdAndUserId(TeamReplyReviewForm replyReviewForm);

    @Select("SELECT t.sys_id id,t.team_id teamId,t.reply_review_form replyReviewForm,t.user_id userId,t.score,t.advice,t.details_id detailsId,r.`review_items` replyReviewFormScore,r.`finnish_count` finnishCount\n" +
            "FROM team_reply_review_form t LEFT JOIN reply_review_form r ON r.`details_id`=t.`details_id` AND r.`team_id`=t.`team_id`\n" +
            "WHERE t.team_id=#{teamId} AND t.details_id=#{detailsId} AND t.user_id=#{userId}")
    List<TeamReplyReviewForm> selTeamReplyReviewFormDetailsByTeamIdAndDetailsIdAndUserId(TeamReplyReviewForm teamReplyReviewForm);

    @Select("SELECT sys_id id,team_id teamId,reply_review_form replyReviewForm,user_id userId,score,advice,details_id detailsId\n" +
            "FROM team_reply_review_form WHERE team_id=#{teamId} AND details_id=#{detailsId} ")
    List<TeamReplyReviewForm> selTeamReplyReviewFormByTeamIdAndDetailsId(TeamReplyReviewForm replyReviewForm);

    @Select("SELECT r.sys_id id,review_items replyReviewForm,task_id taskId,team_id teamId,details_id detailsId,`finnish_count` finnishCount,t.sys_team_name teamName\n" +
            "            FROM reply_review_form  r\n" +
            "            LEFT JOIN team t ON r.`team_id`=t.`sys_id`\n" +
            "            WHERE  details_id=#{detailsId} AND r.`team_id`!=#{teamId}")
    List<TeamReplyReviewForm> selReplyReviewFormByDetailsId(TeamReplyReviewForm teamReplyReviewForm);

    @Select("SELECT sys_id id FROM team_score WHERE team_id=#{teamId}\n" +
            "AND task_id IN(SELECT task_id FROM details WHERE sys_id=#{detailsId})")
    List<String> selTeamScoreIdByTeamIdAndDetailsId(TeamReplyReviewForm teamReplyReviewForm);

    @Insert("INSERT INTO reply_review_form(sys_id,review_items,task_id,team_id,details_id,review_people_num)\n" +
            "VALUES (DEFAULT,#{replyReviewForm},#{taskId},#{teamId},#{detailsId},#{reviewPeopleNum})\n")
    Integer insReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    @Select("SELECT sys_id id,review_items replyReviewForm,task_id taskId,team_id teamId,details_id detailsId \n" +
            "FROM reply_review_form WHERE  details_id=#{detailsId} AND team_id=#{teamId}")
    TeamReplyReviewForm selReplyReviewFormByDetailsIdAndTeamId(TeamReplyReviewForm teamReplyReviewForm);

    @Update("UPDATE reply_review_form SET finnish_count=#{finnishCount} WHERE team_id=#{teamId} AND details_id=#{detailsId}")
    Integer updReplyReviewFormFinnishCountByDetailsIdAndTeamId(TeamReplyReviewForm teamReplyReviewForm);
}