package com.example.scoringsystem.controller;

import com.example.scoringsystem.bean.ResponseData;
import com.example.scoringsystem.bean.Task;
import com.example.scoringsystem.service.DetailsOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/09
 */

@Controller
@Slf4j
@CrossOrigin

public class DetailsOperationController {

    @Autowired
    DetailsOperationService detailsOperationService;


//    @RequestMapping("/details/import")
//    @ResponseBody
//    public ResponseData importDetails(MultipartFile excel, Task task, Model model)
//    {
//        log.info("上传的文件名称："+excel.getOriginalFilename()+"上传的作业名称"+task.toString());
//        task.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
//        task.getCreateUser().setId(task.getCreteUserId());
//        task.getClassRoom().setId(Integer.parseInt(task.getClassRoomId()));
//        ResponseData responseData=new ResponseData();
//        ImportParams params = new ImportParams();
//        params.setTitleRows(1);//一级标题
//        params.setHeadRows(2);//header标题
//        try {
//            List<Details> details = ExcelImportUtil.importExcel(excel.getInputStream(), Details.class, params);
//            task.setDetailsList(details);
//            responseData= new DetaisOprationServiceImpl().importTask(task);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (responseData.getCode()==null)
//        {
//            responseData.setCode("1041");
//            responseData.setMessage("操作错误");
//        }
//        return responseData;
//    }


    @RequestMapping("/details/showTaskList")
    @ResponseBody
    public ResponseData showTaskList(@RequestBody Task task) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage("成功查询到作业列表");
        responseData.setCode("204");
        responseData.setData(detailsOperationService.getAllDetails());
        return responseData;
    }

    @RequestMapping("/details/showTaskInfo")
    @ResponseBody
    public ResponseData showTaskInfo(Task task) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage("成功查询到作业");
        responseData.setCode("200");
        responseData.setData(detailsOperationService.getTaskInfo(task));
        return responseData;
    }

    @RequestMapping("/detials/deleteTask")
    @ResponseBody
    public ResponseData delTask(@RequestBody Task task) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage("成功删除该作业");
        responseData.setCode("204");
        detailsOperationService.delTask(task);
        detailsOperationService.delDetails(task);
        return responseData;
    }
}
