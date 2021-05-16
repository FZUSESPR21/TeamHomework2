package com.example.scoring_system.service.impl;

import com.example.scoring_system.bean.*;
import com.example.scoring_system.mapper.ScoreMapper;
import com.example.scoring_system.mapper.TeamMapper;
import com.example.scoring_system.mapper.UserMapper;
import com.example.scoring_system.service.ScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    @Override
    public ResponseData importScoreDetais(List<Details> detailsList) {
        List<DetailsData> detailsDataList = new ArrayList<>();
        Double total = 0.0;
        ResponseData responseData = new ResponseData();
        for (int i = 0; i < detailsList.size(); i++) {
            Details details = detailsList.get(i);
            log.info("正在导入" + details.toString());
            if (details.getScoreDetail() == null || details.getScoreItem().getScoreItem() == null || details.getScoreRatio() == null ||
                    details.getScoreDetail().size() < 1) {
                //导入失败Excel中存在空字符串
                log.info("导入失败Excel中存在空字符串" + details.toString());
                responseData.setCode("1022");
                responseData.setMessage("导入excel填写不规范（存在未填写项）");
            }

            for (int j = 0; j < details.getScoreDetail().size(); j++) {
                DetailsData detailsData = new DetailsData();
                ScoreDetail scoreDetail = details.getScoreDetail().get(j);
                if (scoreDetail.getRatio() == null || scoreDetail.getScoreDetailName() == null) {
                    //存在空字符串
                    responseData.setCode("1022");
                    responseData.setMessage("导入excel填写不规范（存在未填写项）");
                }
                detailsData.setDetailsName(details.getScoreItem().getScoreItem() + "-" + scoreDetail.getScoreDetailName());
                Double actualRatio = Double.parseDouble(details.getScoreRatio()) * Double.parseDouble(scoreDetail.getRatio()) / 10000;
                total += actualRatio;
                detailsData.setTotalScoreRatio(actualRatio.toString());
                detailsData.setTaskId(details.getTaskId());

                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                detailsData.setCreateTime(simpleDateFormat.format(now));
                detailsDataList.add(detailsData);
            }
        }
        if (Math.abs(total - 1.0) < 0.000001) {
            if (scoreMapper.insDetailsBatch(detailsDataList) > 0) {
                responseData.setCode("200");
                responseData.setMessage("导入成功");
                responseData.setData(detailsList);
            }
        } else {
            responseData.setCode("1021");
            responseData.setMessage("导入excel填写不规范（分数占比之和不等于1）");
        }
        return responseData;
    }

    @Override
    @Transactional
    public ResponseData importTask(Task task) {

//        String nextId= StringUtils.toString(Integer.parseInt(id)+1);
        log.info("即将存入数据库的task:" + task);
        ResponseData responseData = new ResponseData();
        if (task.getId()!=null)
        {

        }
        if (scoreMapper.insTask(task) > 0) {
            String id = scoreMapper.selLastRecordInTask().getId();
            for (int i = 0; i < task.getDetailsList().size(); i++) {
                task.getDetailsList().get(i).setTaskId(id);
            }
            responseData = importScoreDetais(task.getDetailsList());
        } else {
            responseData.setCode("1024");
            responseData.setMessage("作业信息不能存在空项");
        }
        return responseData;
    }

    @Override
    @Transactional
    public ResponseData delTaskAndReference(String taskId) {
        if (scoreMapper.delTask(taskId)>0)
            scoreMapper.delDetails(taskId);
        else
            return new ResponseData("删除失败，任务已经有作业提交，或id错误","1401","[]");
        return new ResponseData("删除成功","200","[]");
    }

    @Override
    public List<Task> getTaskByClassId(Task task) {
        log.info("查询的task号" + task.toString());
        return scoreMapper.selTaskByClassRoomId(task);
    }

    @Override
    @Transactional
    public ResponseData blogWorkSubmit(BlogWork blogWork) {
        log.info(blogWork.toString());
        ResponseData responseData = new ResponseData();
        Task task = new Task();
        task.setId(blogWork.getTaskId());
        blogWork.setTask(task);
        task = scoreMapper.selTaskById(task);
        User user = new User();
        user.setId(blogWork.getUserId());

        if (task == null) {
            responseData.setCode("1061");
            responseData.setMessage("任务错误");
            return responseData;
        }
        if ((user = userMapper.selUserById(user)) == null) {
            responseData.setCode("1062");
            responseData.setMessage("用户号错误");
            return responseData;
        }
        Team team = new Team();
        team.setId(user.getTeamId());
        if ((team = scoreMapper.selTeamById(team)) == null) {
            log.info("找不到团队");
        }

        blogWork.setBlogWorkType(task.getTaskType());
        blogWork.setTeamId(team.getId());
        List<BlogWork> blogWorklist = scoreMapper.selBlogWorkByTaskIdAndUserIdOrTeamId(team.getId(), task.getId(), user.getId());
        if (blogWorklist.size() > 0) {
            responseData.setMessage("作业已提交,请勿重复提交");
            responseData.setCode("1063");
            log.info("返回的数据：" + blogWorklist.get(0));
            responseData.setData(blogWorklist.get(0));
            return responseData;
        }
        log.info("存储入数据库博客作业：" + blogWork.toString());
        log.info(task.toString());
        log.info(team.toString());
        if ((task.getTaskType().equals("团队作业") || task.getTaskType().equals("结对作业")) && task.getId() != null && team.getId() != null) {
            blogWork = checkContributions(blogWork);
            if (blogWork == null) {
                return new ResponseData("贡献率之和不为100", "1121", "");
            }
            scoreMapper.insTeamScore(team.getId(), task.getId(), blogWork.getContributions());
            scoreMapper.insBlogWork(blogWork);
        } else if (task.getTaskType().equals("个人作业")) {
            scoreMapper.insUserScore(user.getId(), task.getId());
            scoreMapper.insBlogWork(blogWork);
        }
        responseData.setCode("200");
        responseData.setMessage("存储成功");
        responseData.setData(blogWork);
        return responseData;
    }

    private BlogWork checkContributions(BlogWork blogWork) {
        StringBuilder contributions = new StringBuilder();
        Double totalRatio = 0.0;
        for (int i = 0; i < blogWork.getContributionList().size(); i++) {
            Contribution contribution = blogWork.getContributionList().get(i);
            totalRatio += Double.parseDouble(contribution.getRatio()) / 100;
            contributions.append(contribution.getAccount() + ":" + contribution.getRatio() + ",");
        }
        if (Math.abs(totalRatio - 1) < 0.000001) {
            String tmp = contributions.toString();
            blogWork.setContributions(tmp.substring(0, tmp.length() - 1));
            return blogWork;
        }
        return null;
    }

    @Override
    public PageInfo<BlogWork> getBlogWorkPageInfoByTaskIdAndClassRoomId(PageRequest pageRequest, Task task) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<BlogWork> blogWorkList = scoreMapper.selBlogWorkByTaskIdAndClassRoomId(task);
        return new PageInfo<>(blogWorkList);
    }

    @Override
    public PageInfo<BlogWork> getBlogWorkPageInfoByClassRoomId(PageRequest pageRequest, Task task) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<BlogWork> blogWorkList = scoreMapper.selBlogWorkByClassRoomId(task);
        return new PageInfo<>(blogWorkList);
    }

//    @Override
//    public PageInfo<BlogWork> getBlogWorkPageInfoByClassRoomId(PageRequest pageRequest, Task task) {
//        int pageNum = pageRequest.getPageNum();
//        int pageSize = pageRequest.getPageSize();
//        PageHelper.startPage(pageNum, pageSize);
//        List<BlogWork> blogWorkList = scoreMapper.selBlogWorkByClassRoomId(task);
//        return new PageInfo<>(blogWorkList);
//        return null;
//    }

    @Override
    public BlogWork getOneTeamBlogWork(BlogWork blogWork) {
        return scoreMapper.selTeamBlogWorkById(blogWork);
    }

    @Override
    public BlogWork getOneUserBlogWork(BlogWork blogWork) {
        return scoreMapper.selUserBlogWorkById(blogWork);
    }

    /**
     * @Description:
     * @Param: blogwork.id, detailsData.id, detailsData.score
     * @return: com.example.scoring_system.bean.BlogWork
     * @Date: 2021/5/5
     */
    @Override
    @Transactional
    public BlogWork scoringBlogWork(BlogWork blogWork, List<DetailsData> detailsDataList) {
        log.info(blogWork.toString());
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$4修改的细则" + detailsDataList);
        BlogWork blogWork1 = scoreMapper.selUserBlogWorkById(blogWork);
        log.info("查询的博客1：" + blogWork1.toString());
        if ((blogWork1.getBlogWorkType().equals("团队作业") || blogWork1.getBlogWorkType().equals("结对作业"))) {
            blogWork1 = dealTeamAndPairBlogWork(blogWork, blogWork1, detailsDataList);
            return blogWork1;
        } else if (blogWork1.getBlogWorkType().equals("个人作业")) {
            blogWork1 = dealIndividualBlogWork(blogWork, blogWork1, detailsDataList);
            return blogWork1;
        }
        return null;
    }

    @Override
    public List<BlogWork> getUserBlogWorkListByUserId(User user) {
        return scoreMapper.selUserBlogWorkListByUserId(user);
    }

    @Override
    public List<BlogWork> getTeamBlogWorkListByUserId(User user) {
        return scoreMapper.selTeamBlogWorkListByUserId(user);
    }

    @Override
    public List<BlogWork> getBlogWorkListByClassIdAndTaskId(Task task) {
        List<BlogWork> blogWorkList = new ArrayList<>();
        log.info("需要查询的task"+task.toString());
        blogWorkList.addAll(scoreMapper.selUserBlogWorkListByClassIdAndTaskId(task));
        blogWorkList.addAll(scoreMapper.selTeamBlogWorkListByClassIdAndTaskId(task));
        return blogWorkList;
    }

    private BlogWork dealIndividualBlogWork(BlogWork blogWork, BlogWork blogWork1, List<DetailsData> detailsDataList) {
        log.info("需要处理的数据：" + blogWork + blogWork1 + detailsDataList);
        blogWork1 = scoreMapper.selUserBlogWorkById(blogWork);
        blogWork1.setTask(scoreMapper.selTaskById(blogWork1.getTask()));
        log.info("数据库查询的：" + blogWork1.toString());
        List<DetailsData> taskDetailsDatalist = blogWork1.getScore().getDetailsDataList();
        log.info("作业细则:" + taskDetailsDatalist);
        taskDetailsDatalist = mergeDetailsData(taskDetailsDatalist, detailsDataList);
        log.info("合并得到的结果:" + taskDetailsDatalist.toString());
        String answer = calculateTotalScore(taskDetailsDatalist);
        Score score = blogWork1.getScore();
        score = scoreMapper.selUserScoreById(score);
        score.setScore(answer);
        //保存细则评分的分数。
        saveIndividualScoreDetails(taskDetailsDatalist, score.getId());
        //评分结束将进行本次最后得分计算。
        if (answer != null) {
            //存储本次作业总分
            scoreMapper.upduserScoreById(score);
            //将作业状态修改至已经批改
            scoreMapper.updBlogWorkIsMarkById(blogWork1);
            User user = blogWork1.getUser();
            log.info("即将操作的用户的信息：" + user.toString());
            //累计到个人总分
            Double totalScore = Double.parseDouble(user.getTotalScore()) +
                    Double.parseDouble(score.getScore()) * Double.parseDouble(blogWork1.getTask().getRatio());
            user.setTotalScore(totalScore.toString());
            log.info("更新totalScore时的user：" + user);
            scoreMapper.updUserTotalScore(user);
        }
        return scoreMapper.selUserBlogWorkById(blogWork);
    }

    private void saveIndividualScoreDetails(List<DetailsData> list, String id) {
        log.info("存储的评分细则：" + list.toString() + "个人评分ID" + id);
        for (int i = 0; i < list.size(); i++) {
            DetailsData detailsData = scoreMapper.selUserScoreDetailsByTeamScoreIdAndDetailsId(id, list.get(i).getId());
            if (detailsData == null || detailsData.getId() == null)
                scoreMapper.insUserScoreDetails(list.get(i), id);
            else
                scoreMapper.updUserScoreDetails(list.get(i), id);
        }
    }

    private BlogWork dealTeamAndPairBlogWork(BlogWork blogWork, BlogWork blogWork1, List<DetailsData> detailsDataList) {
        log.info("需要处理的数据：" + blogWork + blogWork1 + detailsDataList);
        blogWork1 = scoreMapper.selTeamBlogWorkById(blogWork);
        blogWork1.setTask(scoreMapper.selTaskById(blogWork1.getTask()));
        log.info("数据库查询的:" + blogWork1.toString());
        List<DetailsData> taskDetailsDatalist = blogWork1.getScore().getDetailsDataList();
        log.info("作业的细则项：" + taskDetailsDatalist.toString());
        taskDetailsDatalist = mergeDetailsData(taskDetailsDatalist, detailsDataList);
        log.info("合并后得到的结果:" + taskDetailsDatalist);
        String answer = calculateTotalScore(taskDetailsDatalist);
        Score score = blogWork1.getScore();
        score = scoreMapper.selteamScoreById(score);
        log.info("Socre:" + score);
        score.setScore(answer);
        //存储本次作业细则分
        saveTeamScoreDetails(taskDetailsDatalist, score.getId());
        if (answer != null) {
            //存储本次作业总分
            scoreMapper.updteamScoreById(score);
            //将作业状态修改至已经批改
            scoreMapper.updBlogWorkIsMarkById(blogWork1);
            //累积到个人分数
            String contribution = score.getContributions();
            log.info("贡献度" + contribution);
            String[] strings = contribution.split(",");
            Map<String, String> individualScore = new HashMap<>();
            for (int i = 0; i < strings.length; i++) {
                log.info(strings[i]);
                String[] accountAndRatio = strings[i].split(":");
                User user = new User();
                user.setAccount(accountAndRatio[0]);
                user = userMapper.selUserByAccount(user);
                if (user.getTotalScore() == null) {
                    user.setTotalScore("0");
                }
                log.info(blogWork1.getTask().toString());
                log.info("学生原来的成绩:" + Double.parseDouble(user.getTotalScore()) + "ratio:" + Double.parseDouble(blogWork1.getTask().getRatio()) + "本次作业得分:" +
                        Double.parseDouble(score.getScore()));
                Double totalScore = Double.parseDouble(user.getTotalScore()) +
                        Double.parseDouble(score.getScore()) * Double.parseDouble(blogWork1.getTask().getRatio()) * Double.parseDouble(accountAndRatio[1]);
                user.setTotalScore(totalScore.toString());
                log.info("更新totalScore时的user：" + user);
                scoreMapper.updUserTotalScore(user);
            }
        }
        return scoreMapper.selTeamBlogWorkById(blogWork);
    }

    private void saveTeamScoreDetails(List<DetailsData> list, String id) {
        log.info("存储的评分细则：" + list.toString() + "团队评分ID" + id);
        for (int i = 0; i < list.size(); i++) {
            DetailsData detailsData = scoreMapper.selTeamScoreDetailsByTeamScoreIdAndDetailsId(id, list.get(i).getId());
            if (detailsData == null || detailsData.getId() == null)
                scoreMapper.insTeamScoreDetails(list.get(i), id);
            else
                scoreMapper.updTeamScoreDetails(list.get(i), id);
        }
    }

    private List<DetailsData> mergeDetailsData(List<DetailsData> list1, List<DetailsData> list2) {
        log.info("数据库中已经存在的数据：" + list1.toString() + "传入的数据：" + list2.toString());
        for (int i = 0; i < list2.size(); i++) {
            Integer score = Integer.parseInt(list2.get(i).getScore());
            if (score < -100 || score > 100) {
                return null;
            }
            for (int j = 0; j < list1.size(); j++) {
                if (list1.get(j).getId().equals(list2.get(i).getId())) {
                    list1.get(j).setScore(list2.get(i).getScore());
                }
            }
        }
        return list1;
    }

    private String calculateTotalScore(List<DetailsData> detailsDataList) {
        Double ans = 0.0;
        Double tmp = 0.0;
        Double ratio = 0.0;
        for (DetailsData detailsData : detailsDataList) {
            if (detailsData.getScore() == null)
                return null;
            else {
                tmp = Double.parseDouble(detailsData.getScore());
                ratio = Double.parseDouble(detailsData.getTotalScoreRatio());
                ans += tmp * ratio;
            }
        }
        return ans.toString();
    }

    public List<Task> getTaskListByClassIdOrType(Task task) {
        if (task.getTaskType() == null) {
            ClassRoom classRoom = new ClassRoom();
            classRoom.setId(Integer.parseInt(task.getClassRoomId()));
            return scoreMapper.selTaskByClassId(classRoom);
        } else {
            return scoreMapper.selTaskByClassIdAndType(task);
        }
    }

    public List<ClassRoom> getAllClassRoom() {
        return scoreMapper.selAllClass();
    }

    @Override
    public List<DetailsDataWithTeamReplyReviewFormVO> getDetailsDataWithReplyReview(TeamReplyReviewForm teamReplyReviewForm) {
        List<DetailsDataWithTeamReplyReviewFormVO> detailsDataWithTeamReplyReviewFormVOList = new ArrayList<>();
        List<DetailsData> detailsDataList = scoreMapper.selDetailsDataWithReplyReview();

        for (int i = 0; i < detailsDataList.size(); i++) {
            teamReplyReviewForm.setDetailsId(detailsDataList.get(i).getId());
            log.info("查询的" + teamReplyReviewForm);
            TeamReplyReviewForm teamReplyReviewForm1 = scoreMapper.selReplyReviewFormByDetailsIdAndTeamId(teamReplyReviewForm);
            DetailsDataWithTeamReplyReviewFormVO detailsDataWithTeamReplyReviewFormVO;
            if (teamReplyReviewForm1 != null && teamReplyReviewForm1.getId() != null) {
                detailsDataWithTeamReplyReviewFormVO = new DetailsDataWithTeamReplyReviewFormVO(detailsDataList.get(i), true);
            } else {
                detailsDataWithTeamReplyReviewFormVO = new DetailsDataWithTeamReplyReviewFormVO(detailsDataList.get(i), false);
            }
            detailsDataWithTeamReplyReviewFormVOList.add(detailsDataWithTeamReplyReviewFormVO);
        }

        return detailsDataWithTeamReplyReviewFormVOList;
    }

    @Override
    public List<TeamReplyReviewForm> getTeamReplyReviewForm(TeamReplyReviewForm teamReplyReviewForm) {
        List<TeamReplyReviewForm> list = null;
        if (teamReplyReviewForm.getUserId() != null) {
            list = scoreMapper.selTeamReplyReviewFormDetailsByTeamIdAndDetailsIdAndUserId(teamReplyReviewForm);
        }
        if (list == null || list.size() < 1) {
            TeamReplyReviewForm teamReplyReviewForm1 = scoreMapper.selReplyReviewFormByDetailsIdAndTeamId(teamReplyReviewForm);
            if (teamReplyReviewForm1 != null) {
                list = new ArrayList<>();
                list.add(teamReplyReviewForm1);
            }
        }
        return list;
    }

    @Override
    public List<TeamReplyReviewForm> getTeamReplyReviewFormByDetailsIdExceptTeamId(TeamReplyReviewForm teamReplyReviewForm) {
        log.info("输入的数据:" + teamReplyReviewForm);
        return scoreMapper.selReplyReviewFormByDetailsId(teamReplyReviewForm);
    }

    @Override
    public List<TeamReplyReviewFormSimple> getTeamWithIsterminted(TeamReplyReviewForm teamReplyReviewForm) {
        List<TeamReplyReviewForm> teamReplyReviewFormList = getTeamReplyReviewFormByDetailsIdExceptTeamId(teamReplyReviewForm);
        List<TeamReplyReviewFormSimple> teamReplyReviewFormSimpleList = scoreMapper.getTeamReplyReviewFormSimple(teamReplyReviewForm.getTeamId());
        for (int i = 0; i < teamReplyReviewFormList.size(); i++) {
            TeamReplyReviewForm teamReplyReviewForm1 = teamReplyReviewFormList.get(i);
            if (teamReplyReviewForm1.getFinnishCount() != null && teamReplyReviewForm1.getFinnishCount() > 0) {
                for (int j = 0; j < teamReplyReviewFormSimpleList.size(); j++) {
                    log.info(teamReplyReviewFormSimpleList.get(j).getTeamId() + "   " + teamReplyReviewForm1.getTeamId());
                    if (teamReplyReviewFormSimpleList.get(j).getTeamId().equals(teamReplyReviewForm1.getTeamId())) {
                        teamReplyReviewFormSimpleList.set(j, new TeamReplyReviewFormSimple(teamReplyReviewForm1.getTeamId(),
                                teamReplyReviewForm1.getTeamName(), true));
                    }
                }
            }
        }
        return teamReplyReviewFormSimpleList;
    }


    /**
     * @Description: 操作成功返回1，操作失败返回0；
     * @Param: [teamReplyReviewForm]
     * @return: java.lang.Integer
     * @Date: 2021/5/9
     */
    @Override
    @Transactional
    public Integer changeReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm) {
        Integer flag = 1;
        List<TeamReplyReviewForm> teamReplyReviewFormList = scoreMapper.selTeamReplyReviewFormByTeamIdAndDetailsIdAndUserId(teamReplyReviewForm);
        log.info("查询到的:" + teamReplyReviewFormList);
        if (teamReplyReviewFormList.size() > 0) {
            return scoreMapper.updTeamReplyReviewForm(teamReplyReviewForm);
        } else {
            if (scoreMapper.insTeamReplyReviewForm(teamReplyReviewForm) < 1) {
                flag = 0;
            }
        }
        //已评分的评审表
        teamReplyReviewFormList = scoreMapper.selTeamReplyReviewFormByTeamIdAndDetailsIdAndUserId(teamReplyReviewForm);
//        //发布的原始评审表
//        TeamReplyReviewForm replyReviewForm=scoreMapper.selReplyReviewFormByDetailsIdAndTeamId(teamReplyReviewForm);
//        if (teamReplyReviewFormList.size()==Integer.parseInt(replyReviewForm.getReviewPeopleNum()) )
//        {
//            Double score=countAverageScore(teamReplyReviewFormList);
//            DetailsData detailsData=new DetailsData();
//            detailsData.setId(replyReviewForm.getDetailsId());
//            detailsData.setScore(score.toString());
//            List<DetailsData> detailsDataList=new ArrayList<>();
//            detailsDataList.add(detailsData);
//            log.info("存储的答辩总分："+detailsData);
//            String teamScoreId=scoreMapper.selTeamScoreIdByTeamIdAndDetailsId(teamReplyReviewForm);
//            saveTeamScoreDetails(detailsDataList,teamScoreId);
//        }
        return flag;
    }

    @Override
    @Transactional
    public Integer countScore(TeamReplyReviewForm teamReplyReviewForm) {
        List<TeamReplyReviewForm> teamReplyReviewFormList = scoreMapper.selTeamReplyReviewFormByTeamIdAndDetailsId(teamReplyReviewForm);
        TeamReplyReviewForm replyReviewForm = scoreMapper.selReplyReviewFormByDetailsIdAndTeamId(teamReplyReviewForm);
        teamReplyReviewForm.setFinnishCount(1);
        scoreMapper.updReplyReviewFormFinnishCountByDetailsIdAndTeamId(teamReplyReviewForm);
//        if (teamReplyReviewFormList.size()>=Integer.parseInt(replyReviewForm.getReviewPeopleNum()) )
//        {
        log.info("各组评分：" + teamReplyReviewFormList.toString());
        Double score = countAverageScore(teamReplyReviewFormList);
        DetailsData detailsData = new DetailsData();
        detailsData.setId(replyReviewForm.getDetailsId());
        detailsData.setScore(score.toString());
        List<DetailsData> detailsDataList = new ArrayList<>();
        detailsDataList.add(detailsData);
        log.info("存储的答辩总分：" + detailsData);
        List<String> teamScoreIdList = scoreMapper.selTeamScoreIdByTeamIdAndDetailsId(teamReplyReviewForm);
        log.info("分数：" + teamScoreIdList.toString());
        saveTeamScoreDetails(detailsDataList, teamScoreIdList.get(0));
//        }
        return 1;
    }


    private Double countAverageScore(List<TeamReplyReviewForm> teamReplyReviewFormList) {
        Double tmp = 0.0;
        for (int i = 0; i < teamReplyReviewFormList.size(); i++) {
            tmp += Double.parseDouble(teamReplyReviewFormList.get(i).getScore());
        }
        tmp /= teamReplyReviewFormList.size();
        return tmp;
    }

    @Override
    public Integer giveReplyReviewFormDetails(TeamReplyReviewForm teamReplyReviewForm) {
        TeamReplyReviewForm teamReplyReviewForm1 = scoreMapper.selReplyReviewFormByDetailsIdAndTeamId(teamReplyReviewForm);
        if (teamReplyReviewForm1 == null || teamReplyReviewForm1.getId() == null)
            return scoreMapper.insReplyReviewForm(teamReplyReviewForm);
        return 0;
    }

    @Override
    public List<DetailsData> getDetailsDataByTaskId(Task task) {
        return scoreMapper.selDetailsDataByTaskId(task);
    }

    @Override
    public Task getTaskByTaskId(Task task) {
        return scoreMapper.selTaskById(task);
    }
}