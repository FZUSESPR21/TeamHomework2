package com.example.scoringsystem.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithTaskAndScore {
    String subAccount;
    String stuAccount;
    String stuName;

    int taskNum;
    List<Double> taskScores;

    List<zlhGrade> data;

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

        data = new ArrayList<>();
        data.add(new zlhGrade("初始成绩",0.0));
        for (int i=1;i<=taskNum;i++){
            data.add(new zlhGrade("累计"+i,taskScores.get(i)));
        }
        stuAccount = stuAccount.substring(1,stuAccount.length());
        subAccount = stuAccount.substring(stuAccount.length()-3,stuAccount.length());
    }

    public String toString(){
        return String.valueOf(taskNum);
    }
}

