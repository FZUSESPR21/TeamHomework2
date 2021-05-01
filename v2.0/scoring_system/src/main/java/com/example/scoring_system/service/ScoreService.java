package com.example.scoring_system.service;

import com.example.scoring_system.bean.Details;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.Task;

import java.util.List;

public interface ScoreService {
    public ResponseData importScoreDetais(List<Details> details);
    public ResponseData importTask(Task task);
}
