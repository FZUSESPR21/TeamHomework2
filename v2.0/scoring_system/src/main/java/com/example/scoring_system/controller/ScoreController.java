package com.example.scoring_system.controller;

import com.example.scoring_system.bean.*;
import com.example.scoring_system.service.ScoreService;
import com.example.scoring_system.service.UserService;
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
    @Autowired
    UserService userService;

    /**
    * @Description: 查询所有学生的总成绩
    * @Param: []
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
    @RequestMapping("/score/user_score/show")
    @ResponseBody
    public ResponseData showAllUserScore(PageRequest pageRequest)
    {
        ResponseData responseData;
        PageInfo<User> userPageInfo=userService.getUserByRoleWithStudent(pageRequest);
        if (userPageInfo!=null&&userPageInfo.getList()!=null&&userPageInfo.getSize()>0)
        {
            responseData=new ResponseData("查询成功","200",userPageInfo);
        }
        else
        {
            responseData=new ResponseData("查询成功","200","[]");
        }

        return responseData;
    }


    /**
    * @Description: 根据班级号查询相应任务
    * @Param: [task]
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
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

    /**
    * @Description: 博客作业提交
    * @Param: [blogWork]
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
    @RequestMapping("/score/blogwork/submit")
    @ResponseBody
    public ResponseData saveWorkBlog(@RequestBody BlogWork blogWork)
    {
        log.info("接收到的博客作业:"+blogWork.toString());
        return scoreService.blogWorkSubmit(blogWork);
    }

    /**
    * @Description:  展示blogwork 列表
    * @Param: [pageRequest, task]
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
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


    @RequestMapping("/score/task/showlist")
    @ResponseBody
    public ResponseData showTask(ClassRoom classRoom)
    {
        log.info("获取的请求数据:"+classRoom);
        ResponseData responseData;

        List<Task> taskList=scoreService.getTaskListByClassId(classRoom);
        log.info("响应的数据"+taskList);
        responseData=new ResponseData("获取成功","200",taskList);
        return responseData;
    }

    @RequestMapping("/score/class/showlist")
    @ResponseBody
    public ResponseData showClassRoom()
    {
        ResponseData responseData;

        List<ClassRoom> classRooms=scoreService.getAllClassRoom();
        log.info("响应的数据"+classRooms);
        responseData=new ResponseData("获取成功","200",classRooms);
        return responseData;
    }

    /**
    * @Description: 根据blogwork id查询blogwork的详情及相应成绩等
    * @Param: [blogWork]
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
    @RequestMapping("/score/blogwork/details")
    @ResponseBody
    public ResponseData showBlogWorkDetais(BlogWork blogWork)
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

    @RequestMapping("/score/userblogwork/list")
    @ResponseBody
    public ResponseData showUserBlogWorkList(User user)
    {
        log.info("查询的用户:"+user);
        List<BlogWork> blogWorkList=scoreService.getUserBlogWorkListByUserId(user);
        if (blogWorkList!=null&&blogWorkList.size()>0)
        {
            return new ResponseData("查询成功","200",blogWorkList);
        }
        return new ResponseData("查询失败","1101","[]");
    }

    @RequestMapping("/score/teamblogwork/list")
    @ResponseBody
    public ResponseData showTeamBlogWorkList(User user)
    {
        log.info("查询的用户:"+user);
        List<BlogWork> blogWorkList=scoreService.getTeamBlogWorkListByUserId(user);
        if (blogWorkList!=null&&blogWorkList.size()>0)
        {
            return new ResponseData("查询成功","200",blogWorkList);
        }
        return new ResponseData("查询失败","1101","[]");
    }

    /**
    * @Description: 博客评分接口。
    * @Param: [blogWorkScoring]
    * @return: com.example.scoring_system.bean.ResponseData
    * @Date: 2021/5/7
    */
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

    @RequestMapping("/score/teamUser/show")
    @ResponseBody
    public ResponseData showTeamUser(Team team)
    {
        log.info("传入的数据:"+team.toString());
        List<User> userList=userService.getUserListByTeamId(team);
        if (userList==null||userList.size()<=0)
        {
            return new ResponseData("查询失败","1121","[]");
        }
        else
        {
            return new ResponseData("查询成功","200",userList);
        }
    }

    @RequestMapping("/score/teamReplyReview/show")
    @ResponseBody
    public ResponseData showReplyReviewList()
    {
        List<DetailsData> detailsDataList=scoreService.getDetailsDataWithReplyReview();
        if (detailsDataList==null||detailsDataList.size()<=0)
        {
            return new ResponseData("查询失败","1131","[]");
        }
        return new ResponseData("查询成功","200",detailsDataList);
    }

    @RequestMapping("/score/teamReplyReview/details")
    @ResponseBody
    public ResponseData showReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm)
    {
        List<TeamReplyReviewForm> teamReplyReviewFormList=scoreService.getTeamReplyReviewForm(teamReplyReviewForm);
        if (teamReplyReviewFormList==null||teamReplyReviewFormList.size()<=0)
        {
            return new ResponseData("查询失败","1141","[]");
        }
        return new ResponseData("查询成功","200",teamReplyReviewFormList);
    }

    @RequestMapping("/score/teamReplyReview/change")
    @ResponseBody
    public ResponseData changeReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm)
    {
        if (teamReplyReviewForm==null||teamReplyReviewForm.getTeamId()==null||teamReplyReviewForm.getDetailsId()==null)
        {
            return new ResponseData("传入的TeamId,DetailsId不能为空 ","1042","[]");
        }
        if (scoreService.changeReplyReviewFormDetails(teamReplyReviewForm)>0)
        {
            return new ResponseData("评审表修改成功","200","[]");
        }
        return new ResponseData("评审表修改失败","1051","[]");
    }
    
}
