package com.example.scoring_system.bean;

import lombok.Data;

@Data
public class ResponseData {
    String message;
    String code;
    Object data;
    String token;
}
