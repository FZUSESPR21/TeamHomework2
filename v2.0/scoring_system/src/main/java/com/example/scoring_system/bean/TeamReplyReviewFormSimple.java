package com.example.scoring_system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamReplyReviewFormSimple {
    String teamId;
    String teamName;
    boolean terminated;
}
