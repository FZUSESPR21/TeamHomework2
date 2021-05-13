package com.example.scoring_system.service;

import com.example.scoring_system.bean.Details;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.Task;

import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/09
 */
public interface DetailsOperationService {
    public ResponseData importScoreDetails(List<Details> details);
    public ResponseData importTask(Task task);
    public List<Task> getAllDetails();
    public void delDetails(Task task);
    public void delTask(Task task);
    public Task getTaskInfo(Task task);
}
