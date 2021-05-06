package com.example.scoring_system.controller;

import com.example.scoring_system.bean.*;
import com.example.scoring_system.service.ScoreService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@CrossOrigin
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping("/score/task/show")
    @ResponseBody
    public ResponseData showAllTask(Task task)
    {
        ResponseData responseData=new ResponseData();
        log.info("取得的task:"+task);
        if (task.getClassRoomId()==null)
        {
            responseData.setCode("1051");
            responseData.setMessage("班级号错误");
            return responseData;
        }
        List<Task> list=scoreService.getTaskByClassId(task);
        if (list.size()<=0)
        {
            responseData.setCode("1052");
            responseData.setMessage("查询作业失败");
        }
        else
        {
            responseData.setCode("200");
            responseData.setMessage("查询查询成功");
            responseData.setData(list);
        }
        return responseData;
    }

    @RequestMapping("/score/blogwork/submit")
    @ResponseBody
    public ResponseData saveWorkBlog(BlogWork blogWork)
    {
        log.debug("接收到的博客作业:"+blogWork.toString());
        return scoreService.blogWorkSubmit(blogWork);
    }

    @RequestMapping("/score/blogwork/showlist")
    @ResponseBody
    public ResponseData shlowWorkBlog(PageRequest pageRequest, Task task)
    {
        log.info("获取的请求数据:"+pageRequest+task);
        ResponseData responseData;
        if (task.getClassRoomId()==null||task.getId()==null||pageRequest.getPageNum()<=0||pageRequest.getPageSize()<=0)
        {
            responseData=new ResponseData("输入的班级号，任务号，请求数量，大小存在null或0","1071","[]");
            return responseData;
        }
        PageInfo<BlogWork> blogWorkPageInfo=scoreService.getBlogWorkPageInfo(pageRequest,task);
        log.info("响应的数据"+blogWorkPageInfo);
        responseData=new ResponseData("获取成功","200",blogWorkPageInfo);
        return responseData;
    }

    @RequestMapping("/score/blogwork/details")
    @ResponseBody
    public ResponseData showTeamBlogWorkDetais(BlogWork blogWork)
    {
        log.info("获取的请求数据:"+blogWork);
        ResponseData responseData;
        if (blogWork.getId()==null)
        {
            responseData=new ResponseData("博客ID号为空","1081","[]");
            return responseData;
        }
        BlogWork blogWork1=scoreService.getOneUserBlogWork(blogWork);
        if (blogWork1.getBlogWorkType().equals("团队作业")||blogWork1.getBlogWorkType().equals("结对作业"))
        {
            blogWork1=scoreService.getOneTeamBlogWork(blogWork);
        }
        log.info("响应的数据"+blogWork1.toString());
        responseData=new ResponseData("获取成功","200",blogWork1);
        return responseData;
    }

    @RequestMapping("/score/blogwork/scoring")
    @ResponseBody
    public ResponseData scoringBlogWork(@RequestBody BlogWorkScoring blogWorkScoring)
    {
        log.info("传入的数据"+blogWorkScoring.toString());
//        List<DetailsData> detailsDataList=new ArrayList<>();
//        detailsDataList.add(blogWorkScoring.getDetailsData());
        BlogWork blogWork=new BlogWork();
        blogWork.setId(blogWorkScoring.getBlogWorkId());
        if ((blogWork=scoreService.scoringBlogWork(blogWork,blogWorkScoring.getDetailsDatas()))!=null)
        {
            return new ResponseData("评分提交成功","200",blogWork);
        }
        else
        {
            return new ResponseData("评分提交失败","1091","[]");
        }
    }
}
