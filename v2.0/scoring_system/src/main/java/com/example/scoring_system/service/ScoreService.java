package com.example.scoring_system.service;

import com.example.scoring_system.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ScoreService {
    ResponseData importScoreDetais(List<Details> details);

    ResponseData importTask(Task task);

    ResponseData delTaskAndReference(String taskId);

    List<Task> getTaskByClassId(Task task);

    ResponseData blogWorkSubmit(BlogWork blogWork);

    PageInfo<BlogWork> getBlogWorkPageInfoByTaskIdAndClassRoomId(PageRequest pageRequest, Task task);

    BlogWork getOneTeamBlogWork(BlogWork blogWork);

    BlogWork getOneUserBlogWork(BlogWork blogWork);

    BlogWork scoringBlogWork(BlogWork blogWork, List<DetailsData> detailsDataList);

    List<BlogWork> getUserBlogWorkListByUserId(User user);

    List<BlogWork> getTeamBlogWorkListByUserId(User user);

    List<BlogWork> getBlogWorkListByClassIdAndTaskId(Task task);

    List<Task> getTaskListByClassIdOrType(Task task);

    List<ClassRoom> getAllClassRoom();

    List<DetailsDataWithTeamReplyReviewFormVO> getDetailsDataWithReplyReview(TeamReplyReviewForm teamReplyReviewForm);

    List<TeamReplyReviewForm> getTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);

    List<TeamReplyReviewForm> getTeamReplyReviewFormByDetailsIdExceptTeamId(TeamReplyReviewForm teamReplyReviewForm);

    List<TeamReplyReviewFormSimple> getTeamWithIsterminted(TeamReplyReviewForm teamReplyReviewForm);

    Integer changeReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm);

    Integer countScore(TeamReplyReviewForm teamReplyReviewForm);

    Integer giveReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm);

    List<DetailsData> getDetailsDataByTaskId(Task task);

    Task getTaskByTaskId(Task task);

    PageInfo<BlogWork> getBlogWorkPageInfoByClassRoomId(PageRequest pageRequest, Task task);

//    PageInfo<BlogWork> getBlogWorkPageInfoByClassRoomId(PageRequest pageRequest,Task task);
}