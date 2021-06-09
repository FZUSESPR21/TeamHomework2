package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.Details;
import com.example.scoringsystem.bean.ResponseData;
import com.example.scoringsystem.bean.Task;

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
