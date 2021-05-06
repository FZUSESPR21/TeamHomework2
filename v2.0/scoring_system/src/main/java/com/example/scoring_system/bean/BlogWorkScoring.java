package com.example.scoring_system.bean;

import lombok.Data;

import java.util.List;

@Data
public class BlogWorkScoring {
    String blogWorkId;
    List<DetailsData> detailsDatas;
}
