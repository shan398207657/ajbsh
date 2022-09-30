package com.work.ssj.pdf.util;

/**
 * @Auther: admin
 * @Date: 2019/8/10 12:59
 * @Description:
 */

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;


public class PdfTemplateUtil {


    //构造器私有，防止别人通过new对象调用
    private PdfTemplateUtil() {
    }

    /**
     * @param data             模板数据
     * @param templateFileName freemarker模板文件名
     * @return : java.io.ByteArrayOutputStream
     * @auther : $Mr. Liu$
     * @date : 2019/8/9 14:45
     * @description : 通过模板导出pdf文件(有返回值)
     **/

    public static ByteArrayOutputStream createPDF(Map<String, Object> data, String templateFileName,String fName,String path) throws Exception {
        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 指定FreeMarker模板文件的位置
        configuration.setClassForTemplateLoading(PdfTemplateUtil.class, "/templates");
        ITextRenderer renderer = new ITextRenderer();
        OutputStream out = new ByteArrayOutputStream();
        StringWriter writer = new StringWriter();

        try {
            // 设置 css中 的字体样式（暂时仅支持宋体和黑体） 必须，不然中文不显示
            ITextFontResolver fontResolver = renderer.getFontResolver();

            /*String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {*/
                fontResolver.addFont("/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont("/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont("/fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            /*} else {
                fontResolver.addFont("/usr/share/fonts/dejavu/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont("/usr/share/fonts/dejavu/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                fontResolver.addFont("/usr/share/fonts/dejavu/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }*/
            // 设置模板的编码格式
            configuration.setEncoding(Locale.CHINA, "UTF-8");
            // 获取模板文件
            Template template = configuration.getTemplate(templateFileName, "UTF-8");
            // 将数据输出到html中
            template.process(data, writer);
            writer.flush();
            String html = writer.toString();
            renderer.setDocumentFromString(html);

            // 设置模板中的图片路径 （这里的images在resources目录下） 模板中img标签src路径需要相对路径加图片名 如<img src="img/1.jpg"/>
            /*String url = PdfTemplateUtil.class.getClassLoader().getResource("static/img").toURI().toString();
            renderer.getSharedContext().setBaseURL(url);*/
            renderer.layout();
            renderer.createPDF(out, false);

            ByteArrayOutputStream os2 = new ByteArrayOutputStream(1024000);
            OutputStream fileStream = new FileOutputStream(path + "/" + fName);
            renderer.createPDF(os2);
            renderer.finishPDF();
            fileStream.write(os2.toByteArray());
            fileStream.close();
            out.flush();
            return (ByteArrayOutputStream) out;
        }catch (Exception e){
            System.out.println(data);
            return null;
        }finally {
            if (writer != null) {
                writer.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    public static void createPDF3(Map<String, Object> data, String templateFileName,String fName,String path) throws Exception {
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
        data= JSON.parseObject(s,Map.class);
        template.process(data, writer);
        writer.flush();
        String html = writer.toString();
        PDFUtil.htmlToPdf(html,path,fName);
    }

    public static void createPDF4(Map<String, Object> data, String templateFileName,String fName,String path) throws Exception {
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
        data= JSON.parseObject(s,Map.class);
        template.process(data, writer);
        writer.flush();
        String html = writer.toString();
        PDFUtil.htmlToPdf(html,path,fName);
    }
}