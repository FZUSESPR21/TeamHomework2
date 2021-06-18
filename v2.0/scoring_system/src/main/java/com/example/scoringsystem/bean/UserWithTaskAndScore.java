package com.example.scoringsystem.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithTaskAndScore {
    String stuAccount;
    String stuName;

    int taskNum;
    List<Double> taskScores;

    List<TaskResult> taskList;

    public void calculate(){
        taskScores = new ArrayList<>();
        double all = 0;
        taskNum = taskList.size();
        for (int i=0;i<taskList.size();i++){
            taskScores.add(all);
            if (taskList.get(i).score != null)
                all += taskList.get(i).score;
            else
                all += 0;
        }
        taskScores.add(all);
    }

    public String toString(){
        return String.valueOf(taskNum);
    }
}
