package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class BlogWorkForAndroid {
    String id;
    String title;
    String type;
    String deadline;
    String rawScore;
    String proportion;
    String actualScore;
}
