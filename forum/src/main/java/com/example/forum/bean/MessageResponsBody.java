package com.example.forum.bean;

public class MessageResponsBody {
    String code;
    String msg;
    Integer count;
    Message message;

    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Integer getCount()
    {
        return count;
    }
    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Message getMessage() { return message; }
    public void setMessage(Message message)
    {
        this.message = message;
    }
}
