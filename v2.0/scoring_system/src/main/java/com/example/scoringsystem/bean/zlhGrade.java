package com.example.scoringsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class zlhGrade {
        String name;
        Double grade;
        public zlhGrade(String a,Double b){
            name = a;
            grade = b;
        }
}
