package com.work.ssj.thirdtool.email;


import cn.hutool.core.util.StrUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 发送邮件
 */
public class EmailUtil {

    /**
     * 发送邮件
     * @param to 给谁发
     * @param title 标题
     * @param text 发送内容
     */
    public static void send_mail(String to,String title,String text) throws MessagingException, UnsupportedEncodingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.qiye.aliyun.com");
        //发送端口
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("927679969@qq.com","shanshiji1314");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("927679969@qq.com", "阿吉测试","UTF-8"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //设置主题
        message.setSubject(title);
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
    }
    /**
     * 发送邮件
     * @param toUser 给谁发 eg:toUser="xxx@xxx.com,xxx@xxx.com,xxx@xxx.com";
     * @param ccUser 抄送 eg:ccUser="xxx@xxx.com,xxx@xxx.com,xxx@xxx.com";
     * @param bccUser 多个密送地址
     * @param title 标题
     * @param text 发送内容
     * @param attachment 附件
     */
    public static void sendMailOne(String toUser,String ccUser,String bccUser,String title,String text, File attachment) throws MessagingException, UnsupportedEncodingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.qiye.aliyun.com");
        //发送端口
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("927679969@qq.com","shanshiji1314");
            }
        });
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("927679969@qq.com", "阿吉测试","UTF-8"));
        //设置收件人
        if(StrUtil.isNotEmpty(toUser)){
            @SuppressWarnings("static-access")
            InternetAddress[] internetAddressTo = new InternetAddress().parse(toUser);
            message.setRecipients(Message.RecipientType.TO, internetAddressTo);
        }
        // 设置多个抄送地址
        if(StrUtil.isNotEmpty(ccUser)){
            @SuppressWarnings("static-access")
            InternetAddress[] internetAddressCC = new InternetAddress().parse(ccUser);
            message.setRecipients(Message.RecipientType.CC, internetAddressCC);
        }
        // 设置多个密送地址
        if(StrUtil.isNotEmpty(bccUser)){
            @SuppressWarnings("static-access")
            InternetAddress[] internetAddressBCC = new InternetAddress().parse(bccUser);
            message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
        }
        //设置主题
        message.setSubject(title);
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent(text,"text/html;charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        // 添加附件的内容
        if (attachment != null) {

            MimeBodyPart textContent = new MimeBodyPart();
            textContent.setContent(text,"text/html;charset=UTF-8");
            multipart.addBodyPart(textContent);

            BodyPart attachPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(attachment);
            attachPart.setDataHandler(new DataHandler(fileDataSource));
            attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
            multipart.addBodyPart(attachPart);
            // 将multipart对象放到message中
            message.setContent(multipart);
            message.saveChanges();
        }
        //发送一封邮件
        Transport.send(message);
    }
}