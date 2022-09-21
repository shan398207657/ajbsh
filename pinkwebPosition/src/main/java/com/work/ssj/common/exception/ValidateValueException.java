package com.work.ssj.common.exception;

/**
 * @author wjning
 * @date 2021/6/3 16:54
 * @description 验证字段错误
 */
public class ValidateValueException  extends RuntimeException
{

    protected final String message;

    public ValidateValueException(String message)
    {
        this.message = message;
    }

    public ValidateValueException(String message, Throwable e)
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
