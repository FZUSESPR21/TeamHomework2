package com.example.scoring_system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/** 
* @Description: 角色类
* @Author: 曹鑫
* @Date: 2021/4/11 
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String id;
    private String roleName;
    private Set<Permissions> permissions;
}
