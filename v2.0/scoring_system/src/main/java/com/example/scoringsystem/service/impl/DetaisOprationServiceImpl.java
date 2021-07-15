package com.example.scoringsystem.service.impl;

import com.example.scoringsystem.bean.*;
import com.example.scoringsystem.mapper.DetailsOperationMapper;
import com.example.scoringsystem.service.DetailsOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date: 2021/05/09
 */


@Service
@Slf4j
public class DetaisOprationServiceImpl implements DetailsOperationService {

    @Autowired
    DetailsOperationMapper detailsOperationMapper;


    @Override
    public ResponseData importScoreDetails(List<Details> detailsList) {
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
            if (detailsOperationMapper.insDetailsBatch(detailsDataList) > 0) {
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
        String id = detailsOperationMapper.selLastRecordInTask().getId();
        String nextId = StringUtils.toString(Integer.parseInt(id) + 1);
        for (int i = 0; i < task.getDetailsList().size(); i++) {
            task.getDetailsList().get(i).setTaskId(nextId);
        }
        log.info("即将存入数据库的task:" + task);
        ResponseData responseData = new ResponseData();
        if (detailsOperationMapper.insTask(task) > 0) {
            responseData = importScoreDetails(task.getDetailsList());
        } else {
            responseData.setCode("1024");
            responseData.setMessage("作业信息不能存在空项");
        }
        return responseData;
    }

    @Override
    public List<Task> getAllDetails() {
        List<Task> temp = detailsOperationMapper.selDetails();
        return detailsOperationMapper.selDetails();
    }

    @Override
    public Task getTaskInfo(Task task) {
        return detailsOperationMapper.getTaskInfo(task);
    }

    @Override
    public void delDetails(Task task) {
        detailsOperationMapper.delDetails(task.getId());
    }

    @Override
    @Transactional
    public ResponseData delTask(Task task) {
        log.info("进入删除作业");
        if (detailsOperationMapper.selBlogWorkNumsByTaskId(task.getId())>0)
        {
            return  new ResponseData("改作业细则已经有对于提交的作业，不能删除","3001","[]");
        }
        detailsOperationMapper.delDetailsByTaskId(task.getId());
        detailsOperationMapper.delTaks(task);
        return new ResponseData("删除成功","200","[]");
    }
}
