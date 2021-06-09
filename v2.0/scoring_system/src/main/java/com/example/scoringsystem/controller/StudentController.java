package com.example.scoringsystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.scoringsystem.bean.PageRequest;
import com.example.scoringsystem.bean.ResponseData;
import com.example.scoringsystem.bean.User;
import com.example.scoringsystem.mapper.StudentMapper;
import com.example.scoringsystem.service.StudentService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("//student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentMapper studentMapper;

    @RequestMapping("/selSingleStudent/{id}")
    @ResponseBody
    public ResponseData selSingleStudent(@PathVariable String id){
        ResponseData responseData;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean isNumber = pattern.matcher(id).matches();
        if (!isNumber) {
            responseData = new ResponseData("查询失败1","1001","[]");
            return responseData;
        }

        User student = studentService.selSingleStudent(id);
        if (student == null){
            responseData = new ResponseData("查询失败2","1002","[]");
            return responseData;
        }

        responseData = new ResponseData("查询成功","200",student);
        return responseData;
    }

    @RequestMapping("/selAll")
    @ResponseBody
    public List<User> selAllStudent(){
        return studentService.selAll();
    }

    @RequestMapping("/selByPage")
    @ResponseBody
    public ResponseData selByPage(PageRequest pageRequest){
        PageInfo<User> pageInfo = studentService.selByPage(pageRequest);
        return new ResponseData("返回的学生列表","200",pageInfo);
    }

    @RequestMapping("/addSingleStudent")
    @ResponseBody
    public ResponseData addSingleStudent( User user){
        boolean result;
        ResponseData responseData;

        responseData = studentService.isRightStuData(user);
        if (!responseData.getCode().equals("200")){
            return responseData;
        }

        result = studentService.addSingleStudent(user);
        if (!result){
            responseData = new ResponseData("学生账户添加失败","1003","[]");
            return responseData;
        }

        responseData = new ResponseData("学生账户添加成功","200","[]");
        return responseData;
    }

    @RequestMapping("/delStuById/{id}")
    @ResponseBody
    public ResponseData delStudent(@PathVariable String id){
        boolean result;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean isNumber = pattern.matcher(id).matches();
        if (!isNumber) {
            return new ResponseData("删除失败","1001","[]");
        }
        User user = studentMapper.selStuById(id);
        if (user == null){
            return new ResponseData("没有这个学生账户","1002","[]");
        }

        result = studentService.delStudent(id);
        if (result){
            return new ResponseData("删除学生成功","200","[]");
        }
        return new ResponseData("删除失败","1003","[]");
    }

    @RequestMapping("/updStudent1")
    @ResponseBody
    public ResponseData updStudent1(@RequestBody User user){
        if (user == null){
            return new ResponseData("没有要修改学生数据","1001","[]");
        }
//        if (user.getAccount() == null || user.getUserName() == null){
//            return new ResponseData("没有要修改学生账户信息","1002","[]");
//        }
        log.info(user.toString());
        User user1 = studentMapper.selStuById(user.getId());
        if (user1 == null){
            return new ResponseData("没有这个学生账户","1002","[]");
        }

        boolean result = studentService.updStudent1(user);
        if (result){
            return new ResponseData("修改成功","200","[]");
        }
        return new ResponseData("修改失败","1001","[]");
    }

    @RequestMapping("/updStudent2")
    @ResponseBody
    public ResponseData updStudent2(@RequestBody User user){
        if (user == null){
            return new ResponseData("没有要修改学生数据","1001","[]");
        }
        if (user.getAccount() == null || user.getUserName() == null){
            return new ResponseData("没有要修改学生账户信息","1002","[]");
        }

        boolean result = studentService.updStudent2(user);
        if (result){
            return new ResponseData("修改成功","200","[]");
        }
        return new ResponseData("修改失败","1001","[]");
    }

    @RequestMapping("/updStudent3")
    @ResponseBody
    public ResponseData updStudent3(@RequestBody User user){
        if (user == null){
            return new ResponseData("没有要修改学生数据","1001","[]");
        }
        if (user.getAccount() == null || user.getUserName() == null){
            return new ResponseData("没有要修改学生账户信息","1002","[]");
        }

        boolean result = studentService.updStudent3(user);
        if (result){
            return new ResponseData("修改成功","200","[]");
        }
        return new ResponseData("修改失败","1001","[]");
    }

    @RequestMapping("/importAll")
    @ResponseBody
    public ResponseData importAll(MultipartFile excel){
        int size = 0;
        log.info("上传的文件名称：" + excel.getOriginalFilename());
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(1);//header标题
        try {
            List<User> studentList = ExcelImportUtil.importExcel(excel.getInputStream(), User.class, params);
            log.error("导入的数量:" + studentList.size());
            size = studentService.insStudentBatch(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("成功插入学生数据"+String.valueOf(size)+"条","200","[]");
    }

}
