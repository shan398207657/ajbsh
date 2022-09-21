package com.work.ssj.common.exception.file;

/**
 * OSS信息异常类
 * 
 * @author shansj
 */
public class OssException extends RuntimeException
{
    private static final long serialVersionUID = 2146840966262730160L;

    public OssException(String msg)
    {
        super(msg);
    }
}