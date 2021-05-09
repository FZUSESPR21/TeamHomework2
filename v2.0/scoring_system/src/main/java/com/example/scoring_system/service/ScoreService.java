package com.example.scoring_system.service;

import com.example.scoring_system.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ScoreService {
    ResponseData importScoreDetais(List<Details> details);
    ResponseData importTask(Task task);
    List<Task> getTaskByClassId(Task task);
    ResponseData blogWorkSubmit(BlogWork blogWork);
    PageInfo<BlogWork> getBlogWorkPageInfo(PageRequest pageRequest, Task task);
    BlogWork getOneTeamBlogWork(BlogWork blogWork);
    BlogWork getOneUserBlogWork(BlogWork blogWork);
    BlogWork scoringBlogWork(BlogWork blogWork,List<DetailsData>  detailsDataList);
    List<BlogWork> getUserBlogWorkListByUserId(User user);
    List<BlogWork> getTeamBlogWorkListByUserId(User user);
    List<Task> getTaskListByClassId(ClassRoom classRoom);
    List<ClassRoom> getAllClassRoom();
    List<DetailsData> getDetailsDataWithReplyReview();
    List<TeamReplyReviewForm> getTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm);
    Integer changeReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm);
}