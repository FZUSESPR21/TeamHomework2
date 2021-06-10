package com.example.scoringsystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    String message;
    String code;
    Object data;
//    String token;
}
