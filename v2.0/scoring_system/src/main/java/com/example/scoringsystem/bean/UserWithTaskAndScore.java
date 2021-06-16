package com.example.scoringsystem.bean;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserWithTaskAndScore {
    String stuAccount;
    String stuName;

    Integer taskNum;
    double[] taskScores;

    List<TaskResult> taskList;

    public void calculate(){
        double all = 0;
        taskNum = taskList.size();
        for (int i=0;i<taskList.size();i++){
            taskScores[i] = all;
            all += taskList.get(i).score;
        }
    }
}
