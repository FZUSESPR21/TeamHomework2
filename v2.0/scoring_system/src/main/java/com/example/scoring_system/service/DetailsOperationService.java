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
    ResponseData importScoreDetails(List<Details> details);

    ResponseData importTask(Task task);

    List<Task> getAllDetails();

    void delDetails(Task task);

    void delTask(Task task);

    Task getTaskInfo(Task task);
}
