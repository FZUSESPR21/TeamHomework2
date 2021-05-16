package com.example.scoring_system.service.impl;


import com.example.scoring_system.bean.Permissions;
import com.example.scoring_system.bean.Role;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.mapper.UserMapper;
import com.example.scoring_system.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String getMapbyName) {
        return getMapByName(getMapbyName);
    }

    public User getUserByAccount(String account) {
        User user = new User();
        user.setAccount(account);
        return userMapper.selUserByAccount(user);
    }

    /**
     * @Description: 模拟数据库查询
     * @Param: [userName]
     * @return: com.example.sblearn.bean.User
     * @Date: 2021/4/14
     */
    private User getMapByName(String userName) {
        log.error("查询的名称" + userName);
        System.out.println("查询的名称" + userName);
        Permissions permissions1 = new Permissions("1", "query");
        Permissions permissions2 = new Permissions("2", "add");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role("1", "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User("1", "lisi", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUserName(), user);

        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role("2", "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }

    /**
     * @Description: 测试mapper方法
     * @Param: []
     * @return: void
     * @Date: 2021/4/16
     */
    public void testMapper() {
        User user = userMapper.selRolesByUserName("root");
        System.out.println(user.getUserName());
        for (Role r : user.getRoles()) {
            System.out.print(r.getRoleName() + " ");
        }
    }

    public User selRolesByUserName(String username) {
        return userMapper.selRolesByUserName(username);
    }

    @Override
    public List<User> selAllStudentUser() {
        return userMapper.selAllStudentUser();
    }

    @Override
    public List<User> getAllStudentUserByClassId(User user) {
        return userMapper.selAllStudentUserByClassId(user);
    }
}
