package com.work.ssj.thirdtool.pdf;

import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;


public class PdfTemplateUtil {


    //构造器私有，防止别人通过new对象调用
    private PdfTemplateUtil() {
    }


    public static void createPDF(Map<?,?> data, String templateFileName, String fName, String path) throws Exception {
        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 指定FreeMarker模板文件的位置
        configuration.setClassForTemplateLoading(PdfTemplateUtil.class, "/templates");
        StringWriter writer = new StringWriter();
        // 设置模板的编码格式
        configuration.setEncoding(Locale.CHINA, "UTF-8");
        // 获取模板文件
        Template template = configuration.getTemplate(templateFileName, "UTF-8");
        // 将数据输出到html中
        String s= JSON.toJSONString(data);
        s=s.replace("&", "&amp;");
        s=s.replace("<", "&lt;");
        s=s.replace("%%", "<br/>");
        data=JSON.parseObject(s,Map.class);
        template.process(data, writer);
        writer.flush();
        String html = writer.toString();
        PDFUtil.htmlToPdf(html,path,fName);
    }

}