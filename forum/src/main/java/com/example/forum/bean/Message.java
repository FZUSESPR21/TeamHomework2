package com.example.forum.bean;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

public class Message
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
=======
public class Message {
    String id;
    String content;
    String writerId;
    String time;
    @Override
    public String toString()
    {
        return "Message{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", writerId='" + writerId + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }

    public String getWriterId(){ return writerId; }
    public void setWriterId(){ this.writerId = writerId; }

    public String getTime(){ return time; }
    public void setTime(String time){ this.time = time; }

>>>>>>> 8c438ae10cd517d9f701351e0418429a1e084ce3
}
