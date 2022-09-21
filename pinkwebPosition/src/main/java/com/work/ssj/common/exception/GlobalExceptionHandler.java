package com.work.ssj.common.exception;

import com.work.ssj.common.core.domain.R;
import com.work.ssj.common.exception.user.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理器
 * @author shansj
 * @author lucas
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public R handleException(HttpRequestMethodNotSupportedException e)
    {
        logger.error(e.getMessage(), e);
        return R.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e)
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        logger.error(e.getCause().getMessage());
        logger.error("运行时异常:",e);
        return R.error(e.getMessage());
    }


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(BusinessException.class)
    public R businessError(BusinessException e)
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        logger.error("业务错误:{}", e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 拦截用户运行时异常
     */
    @ExceptionHandler(UserException.class)
    public R userError(UserException e)
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        logger.error("业务错误:{}", e.getMessage());
        return R.error("错误:" + e.getMessage());
    }


    /**
     * 拦截未知验证字段异常
     */
    @ExceptionHandler(ValidateValueException.class)
    public R validateError(ValidateValueException e)
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        logger.error("ERROR:", e);
        return R.error(e.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(UnivException.class)
    public R handleWindException(UnivException e)
    {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e)
    {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) throws Exception
    {
        logger.error(e.getMessage(), e);
        return R.error("服务器错误，请联系管理员");
    }

    /**
     * 捕获并处理未授权异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R handle401(UnauthorizedException e)
    {
        return R.error(401, e.getMessage());
    }

    // 验证码错误
    @ExceptionHandler(ValidateCodeException.class)
    public R handleCaptcha(ValidateCodeException e)
    {
        return R.error(e.getMessage());
    }
}