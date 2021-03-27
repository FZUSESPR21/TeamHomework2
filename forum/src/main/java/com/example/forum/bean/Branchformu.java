package com.example.forum.bean;

import java.util.ArrayList;
import java.util.List;

public class Branchformu
{
    String id;
    String theme;
    String message;
    List<String> listMessage=new ArrayList<>();
    String joinPaper;

    public List<String> getListMessage()
    {
        return listMessage;
    }

    public void setListMessage(List<String> listMessage)
    {
        this.listMessage = listMessage;
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

    public String getTheme()
    {
        return theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
