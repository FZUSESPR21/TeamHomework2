package com.example.forum.bean;

import java.util.ArrayList;
import java.util.List;

public class Meeting
{
    String id;
    String name;
    String chairManId;
    String branchforumId;
    String startTime;
    String joinPaper;
    List<Branchformu>  branchformuList=new ArrayList<>();


    public List<Branchformu> getBranchformuList()
    {
        return branchformuList;
    }

    public void setBranchformuList(List<Branchformu> branchformuList)
    {
        this.branchformuList = branchformuList;
    }

    public String getJoinPaper()
    {
        return joinPaper;
    }

    public void setJoinPaper(String joinPaper)
    {
        this.joinPaper = joinPaper;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getChairManId()
    {
        return chairManId;
    }

    public void setChairManId(String chairManId)
    {
        this.chairManId = chairManId;
    }

    public String getBranchforumId()
    {
        return branchforumId;
    }

    public void setBranchforumId(String branchforumId)
    {
        this.branchforumId = branchforumId;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
}
