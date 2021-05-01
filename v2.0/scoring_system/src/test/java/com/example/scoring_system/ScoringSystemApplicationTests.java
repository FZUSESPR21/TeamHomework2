package com.example.scoring_system;

import com.example.scoring_system.bean.*;
import com.example.scoring_system.mapper.ScoreMapper;
import com.example.scoring_system.mapper.UserMapper;
import com.example.scoring_system.service.ScoreService;
import com.example.scoring_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ScoringSystemApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    ScoreMapper scoreMapper;
    @Test
    void selRolesByUserName() {

    }

    void contextLoads() {
//        User user=userMapper.selRolesByUserName("root");
//        System.out.println(user.getUser_name());
//        for (Role r:user.getRoles())
//        {
//            System.out.print(r.getRoleName()+" ");
//            for (Permissions p:r.getPermissions())
//            {
//                System.out.println(p.getPermissionsName());
//            }
//        }
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setAccount("S221801201");
        user.setUserName("getusername");
        userList.add(user);
        System.out.println(userMapper.insUserBatch(userList));
    }

//    @Test
    void testInsBatcher()
    {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setAccount("S221801202");
        user.setUserName("getusername2");
        userList.add(user);
        System.out.println(userService.insUserBatch(userList));
    }


//    @Test
    void testScoreService()
    {
        List<DetailsData> detailsDataList=new ArrayList<>();
        DetailsData details1=new DetailsData();
        details1.setCreateTime("2020/02/20");
        details1.setTaskId("1");
        details1.setCreateUserId("1");
        details1.setDetailsName("test");
        details1.setTotalScoreRatio("5");
        detailsDataList.add(details1);
        scoreMapper.insDetailsBatch(detailsDataList);
    }

    @Test
    void testScoreMapper()
    {
        Task task=new Task();
        task.setBegineTime("2020/02/20");
        task.setDeadline("2020/02/20");
        ClassRoom classRoom=new ClassRoom();
        classRoom.setId(1);
        task.setClassRoom(classRoom);
        task.setCreateTime("2020/02/20");
        User user=new User();
        user.setId("1");
        task.setCreateUser(user);
        scoreMapper.insTask(task);
    }
}
