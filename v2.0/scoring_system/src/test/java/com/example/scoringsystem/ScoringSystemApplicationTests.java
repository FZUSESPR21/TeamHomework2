package com.example.scoringsystem;

import com.example.scoringsystem.bean.*;
import com.example.scoringsystem.mapper.ScoreMapper;
import com.example.scoringsystem.mapper.UserMapper;
import com.example.scoringsystem.service.AssistantService;
import com.example.scoringsystem.service.ScoreService;
import com.example.scoringsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
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

    @Autowired
    AssistantService assistantService;
//    @Test
    void selRolesByUserName() {
        BlogWork blogWork=new BlogWork();
        blogWork.setId("17");
        System.out.println(scoreMapper.selUserBlogWorkById(blogWork));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis()
    {
        int i=3;
        System.out.println(1.0/i);
//        User user=new User();
//        user.setAccount("A000002");
//        user.setPassword("123456xm");
//        user.setUserName("GreyZeng");
//        assistantService.addAssistant(user);
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
//        List<User> userList=new ArrayList<>();
//        User user=new User();
//        user.setAccount("S221801201");
//        user.setUserName("getusername");
//        userList.add(user);
//        System.out.println(userMapper.insUserBatch(userList));

    }

//    @Test
//    void testInsBatcher()
//    {
//        List<User> userList=new ArrayList<>();
//        User user=new User();
//        user.setAccount("S221801202");
//        user.setUserName("getusername2");
//        userList.add(user);
//        System.out.println(userService.insUserBatch(userList));
//    }


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

}
