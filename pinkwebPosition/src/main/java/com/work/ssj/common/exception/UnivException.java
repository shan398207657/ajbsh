package com.work.ssj.common.exception;

/**
 * shansj异常类
 * @author shansj
 * @author lucas
 */
public class UnivException extends RuntimeException
{
    //
    private static final long serialVersionUID = 3640068073161175965L;

    private String            msg;

    private int               code             = 500;

    public UnivException(String msg)
    {
        super(msg);
        this.msg = msg;
    }

    public UnivException(String msg, Throwable e)
    {
        super(msg, e);
        this.msg = msg;
    }

    public UnivException(String msg, int code)
    {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public UnivException(String msg, int code, Throwable e)
    {
        super(msg, e);
        this.msg = msg;
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

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}