package com.work.ssj.common.exception.user;


/**
 * 用户信息异常类
 */
public class UserException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected final String message;

    public UserException(String message)
    {
        this.message = message;
    }

    public UserException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
