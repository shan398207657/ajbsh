package com.work.ssj.thirdtool.pdf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 * Word文档工具类
 */
public class WordUtil {

    /**
     * 使用FreeMarker自动生成Word文档
     * @param dataMap   生成Word文档所需要的数据
     * @param fileName  生成Word文档的全路径名称
     */
    public static void generateWord(Map<String, Object> dataMap, String fileName, String path) throws Exception {
        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");

        configuration.setClassForTemplateLoading(PdfTemplateUtil.class, "/templates");
        configuration.setEncoding(Locale.CHINA, "UTF-8");
        Template t = configuration.getTemplate("030.ftl", "UTF-8");
        // 创建一个Word文档的输出流
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"/"+fileName), StandardCharsets.UTF_8));
        //FreeMarker使用Word模板和数据生成Word文档
        t.process(dataMap, out);
        out.flush();
        out.close();
    }

}
