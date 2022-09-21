package com.work.ssj.datasources.aspect;

import com.work.ssj.datasources.DataSourceNames;
import com.work.ssj.datasources.DynamicDataSource;
import com.work.ssj.datasources.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author zh
 * @email 45838976@qq.com
 * @date 2017/9/16 22:20
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

//    @Pointcut("execution(* com.work.ssj.*.*(..))")
    @Pointcut("execution(* com.work.ssj.home.mapper.MyBaseMapper.*(..))")
    private void myBaseMapperAspect() {
    }

    @Before("myBaseMapperAspect()")
    public void before(JoinPoint point) {
        DynamicDataSource.setDataSource(DataSourceNames.OA);
    }

    @After("myBaseMapperAspect()")
    public void after() {
        DynamicDataSource.clearDataSource();
    }

    @Around("@within(com.work.ssj.datasources.annotation.DataSource) || @annotation(com.work.ssj.datasources.annotation.DataSource)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSource clsDs = point.getTarget().getClass().getAnnotation(DataSource.class);

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);

        if(ds != null){
            clsDs = ds;
        }
        if(clsDs != null){
            DynamicDataSource.setDataSource(clsDs.name());
            logger.debug("set datasource is " + clsDs.name());
        } else{
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
