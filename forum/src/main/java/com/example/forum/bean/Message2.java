package com.example.forum.bean;

import java.util.ArrayList;
import java.util.List;

public class Message2
{
    List<Branchformu> branchformuList=new ArrayList<>();

    public List<Branchformu> getBranchformuList()
    {
        return branchformuList;
    }

    public void setBranchformuList(List<Branchformu> branchformuList)
    {
        this.branchformuList = branchformuList;
    }
}
