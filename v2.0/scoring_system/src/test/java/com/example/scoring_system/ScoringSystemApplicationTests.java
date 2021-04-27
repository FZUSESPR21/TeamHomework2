package com.example.scoring_system;

import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.UserMapper;
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

    @Test
    void testInsBatcher()
    {
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setAccount("S221801202");
        user.setUserName("getusername2");
        userList.add(user);
        System.out.println(userService.insUserBatch(userList));
    }
}
