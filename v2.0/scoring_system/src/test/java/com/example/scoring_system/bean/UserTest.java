package com.example.scoring_system.bean;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class UserTest {
    public ArrayList<User> getUsers()
    {
        ArrayList<User> userList=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            User user=new User();
            user.setId(i+"");
            user.setUserName("小鹿"+i);
            user.setPassword("123456");
            user.setPerms("无"+i);
            userList.add(user);
        }
        return userList;
    }


    public static void main(String[] args) throws IOException {
        UserTest userTest=new UserTest();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户列表","测试"),User.class, userTest.getUsers());
        FileOutputStream outputStream=new
                FileOutputStream("E:\\其他\\三下\\软件工程\\团队作业\\实现\\springBoot_shiro\\src\\main\\resources\\static\\excel\\aa.xls");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }


}