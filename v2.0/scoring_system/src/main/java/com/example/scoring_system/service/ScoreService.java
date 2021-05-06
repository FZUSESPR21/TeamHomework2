package com.example.scoring_system.service;

import com.example.scoring_system.bean.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ScoreService {
    public ResponseData importScoreDetais(List<Details> details);
    public ResponseData importTask(Task task);
    public List<Task> getTaskByClassId(Task task);
    public ResponseData blogWorkSubmit(BlogWork blogWork);
    public PageInfo<BlogWork> getBlogWorkPageInfo(PageRequest pageRequest, Task task);
    public BlogWork getOneTeamBlogWork(BlogWork blogWork);
    public BlogWork getOneUserBlogWork(BlogWork blogWork);
    public BlogWork scoringBlogWork(BlogWork blogWork,List<DetailsData>  detailsDataList);
}
