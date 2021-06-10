package com.example.scoringsystem.service;

import com.example.scoringsystem.bean.User;

import java.util.List;

public interface AssistantService {
    /** 
    * @Description:  
    * @Param: [] 
    * @return: java.util.List<com.example.scoringsystem.bean.User> 
    * @Date: 2021/6/9 
    */
    List<User> showAllAssistant();

    /** 
    * @Description:  
    * @Param: [user] 
    * @return: void 
    * @Date: 2021/6/9 
    */
    void addAssistant(User user);

    /** 
    * @Description:  
    * @Param: [] 
    * @return: void 
    * @Date: 2021/6/9 
    */
    void updateAssistant();

    /** 
    * @Description:  
    * @Param: [] 
    * @return: void 
    * @Date: 2021/6/9 
    */
    void delAssistant();
}
