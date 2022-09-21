package com.work.ssj.common.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * @author wjning
 * @date 2021/9/6 13:52
 * @description 自定义将日志存放到数据库
 */

@Configuration
public class LogDBAppender extends DBAppenderBase<ILoggingEvent> {

    protected static final Method GET_GENERATED_KEYS_METHOD;
    //插入sql
    protected String insertSQL;
    // message 日志内容
    static final int MESSAGE = 1;
    // level_string
    static final int LEVEL_STRING = 2;
    // created_time 时间
    static final int CREATE_TIME = 3;
    // logger_name 全类名
    static final int LOGGER_NAME = 4;

    static {
        Method getGeneratedKeysMethod;
        try {
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        insertSQL = buildInsertSQL();
        super.start();
    }

    // 自己写新增sql语句
    private static String buildInsertSQL() {
        return "INSERT INTO customs_logging (`message`,`level_string`,`created_time`,`logger_name`)" +
                "VALUES (?,?,?,?)";
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    /**
     * 主要修改的方法
     */
    @SneakyThrows
    private void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) {
            String message = event.getFormattedMessage();
//        System.err.println(message);
//            String replace = URLDecoder.decode(message, "UTF8");
//            stmt.setString(MESSAGE, replace);
//            stmt.setString(LEVEL_STRING, event.getLevel().toString());
//            stmt.setTimestamp(CREATE_TIME, new Timestamp(event.getTimeStamp()));
//            stmt.setString(LOGGER_NAME, event.getLoggerName());
    }

    @Override
    protected void subAppend(ILoggingEvent eventObject, Connection connection, PreparedStatement statement) throws Throwable {
        bindLoggingEventWithInsertStatement(statement, eventObject);
        int updateCount = statement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }

    @Override
    protected void secondarySubAppend(ILoggingEvent eventObject, Connection connection, long eventId) {
    }
}
