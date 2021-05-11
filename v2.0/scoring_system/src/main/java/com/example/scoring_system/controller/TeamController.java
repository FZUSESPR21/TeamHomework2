package com.example.scoring_system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.scoring_system.bean.*;

import com.example.scoring_system.service.TeamService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@CrossOrigin
@Controller
@RequestMapping("//team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @RequestMapping("/selTeamByPage")
    @ResponseBody
    public ResponseData selTeam(@RequestBody PageRequest pageRequest){
        PageInfo<Team> pageInfo = teamService.selTeamByPage(pageRequest);
        return new ResponseData("返回的团队列表","200",pageInfo);
    }

    @RequestMapping("/selAllTeamMember")
    @ResponseBody
    public ResponseData selAllTeamMember(@RequestBody Team team){
        List<User> studentList = teamService.selAllTeamMember(team);
        if (studentList != null){
            return new ResponseData("成功返回该组的所有学生信息","200",studentList);
        }
        return new ResponseData("没有该组相关信息","1001","[]");
    }

    @RequestMapping("/delTeam")
    @ResponseBody
    public ResponseData delTeam(@RequestBody Team team){
        boolean result = teamService.delTeam(team);
        if (result){
            return new ResponseData("删除成功","200","[]");
        }
        return new ResponseData("删除失败","1001","[]");
    }

    @RequestMapping("/addSingleTeam")
    @ResponseBody
    public ResponseData addSingleTeam(@RequestBody TeamForImport team){
        boolean result;
        result = teamService.addSingleTeam(team);
        if (result) {
            return  new ResponseData("团队增加成功","200","[]");
        }
        return  new ResponseData("团队增加失败","1001","[]");
    }


    @RequestMapping("/import")
    @ResponseBody
    public ResponseData importTeam(MultipartFile excel) {
        Integer size = 0;
        log.info("上传的文件名称：" + excel.getOriginalFilename());
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(1);//header标题
        try {
            List<TeamForImport> teamList = ExcelImportUtil.importExcel(excel.getInputStream(), TeamForImport.class, params);
            size = teamList.size();
            log.error("导入的数量:" + teamList.size());
            size = teamService.insTeamBatch(teamList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("成功导入团队数："+size.toString(),"200","[]");
    }

    @RequestMapping("/updTeam")
    @ResponseBody
    public ResponseData importTeam(@RequestBody Team team) {
        Boolean result = teamService.updTeam(team);
        if (result) {
            return new ResponseData("团队信息修改成功","200","[]");
        }
        return new ResponseData("团队信息修改失败","1001","[]");
    }
}
