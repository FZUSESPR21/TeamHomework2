package com.example.forum.bean;

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

}
