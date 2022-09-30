package com.work.ssj.pdf.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.UUID;


public  class PDFUtil {

    public static HttpServletResponse setPdfResponse(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        return response;
    }

    public static void printLetter(String filepath, OutputStream outputStream){
        FileInputStream inputStream = null;
        try {
            File file = new File(filepath);
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int)file.length()];
            inputStream.read(data);
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void htmlToPdf(String htmlContent, String filePath, String fName )  throws Exception {

        FileUtils.forceMkdir(new File(filePath+"/1"+fName).getParentFile());
        try (OutputStream fileStream = new FileOutputStream(filePath+"/1"+fName)) {
            ITextRenderer textRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = textRenderer.getFontResolver();

            String agreementBody = htmlContent;
//            agreementBody = agreementBody.replace("&nbsp;", " ");
//            agreementBody = agreementBody.replace("&ndash;", "–");
//            agreementBody = agreementBody.replace("&mdash;", "—");
//            agreementBody = agreementBody.replace("&lsquo;", "‘");
//            agreementBody = agreementBody.replace("&rsquo;", "’");
//            agreementBody = agreementBody.replace("&sbquo;", "‚");
//            agreementBody = agreementBody.replace("&ldquo;", "“");
//            agreementBody = agreementBody.replace("&rdquo;", "”");
//            agreementBody = agreementBody.replace("&bdquo;", "„");
//            agreementBody = agreementBody.replace("&prime;", "′");
//            agreementBody = agreementBody.replace("&Prime;", "″");
//            agreementBody = agreementBody.replace("&lsaquo;", "‹");
//            agreementBody = agreementBody.replace("&lt;", "<");
//            agreementBody = agreementBody.replace("&rsaquo;", "›");
//            agreementBody = agreementBody.replace("&gt;", "›");
//            agreementBody = agreementBody.replace("&oline;", "‾");
//            agreementBody = agreementBody.replace("&times;", "×");
//            agreementBody = agreementBody.replace("&middot;", "·");
//            agreementBody = agreementBody.replace("&euro;", "€");


            textRenderer.setDocumentFromString(agreementBody, null);
            fontResolver.addFont("/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("/fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


            textRenderer.layout();
            textRenderer.createPDF(fileStream, true);
            PDFUtil.addPageNum(filePath+"/1"+fName,filePath+"/"+fName);
        }catch (Exception e){
            throw e;
        }
    }

    //PDF文件添加页码
    //给PDF文件添加页码
    public static String addPageNum (String orgPdfPath, String outputPdfPath) throws IOException, DocumentException {
        // 输出文件流
        FileOutputStream fos = new FileOutputStream(outputPdfPath);
        // 新建文档，默认A4大小
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        // 设置页面监听事件，必须在open方法前
        writer.setPageEvent((PdfPageEvent) new PageNumPdfPageEvent());
        document.open();
        // PDF内容体
        PdfContentByte pdfContent = writer.getDirectContent();
        // 读取 源PDF文件，进行一页一页复制，才能触发 添加页码的  页面监听事件
        PdfReader reader = new PdfReader(orgPdfPath);
        // 获取 源文件总页数
        int num = reader.getNumberOfPages();
//        System.out.println("总页数：" + num);
        // 页面数是从1开始的
        boolean pageEmpty = false;
        for (int i = 1; i <= num; i++) {
            document.newPage();
            // 设置空页码进行展示
//            if(i < 4){
//                pageEmpty = true;
//            }

            writer.setPageEmpty(pageEmpty);
            document.setPageCount(num);
            PdfImportedPage page = writer.getImportedPage(reader, i);
            /*System.out.println("宽度："+page.getWidth());
            System.out.println("高度："+page.getHeight());*/

            // 复制好的页面，添加到内容去，触发事件监听
            pdfContent.addTemplate(page, 0, 0);
        }
        document.close();
        reader.close();
        File file = new File(orgPdfPath);// 读取
        if(file.exists()){
            file.delete();
        }
        writer.close();
        return outputPdfPath ;
    }

    /**
     * 为PDF文件添加页码（已生成的pdf文件）
     */
    public static class PageNumPdfPageEvent extends PdfPageEventHelper {

        public void onEndPage(PdfWriter writer, Document document) {
            try {
                // PDF文档内容
                PdfContentByte pdfContent = writer.getDirectContent();

                pdfContent.saveState();
                pdfContent.beginText();

                int footerFontSize = 12 ;
                int pageFontSize = 8 ;
                // 解决页码中文无法显示 或者 显示为乱码的问题
                // 但是必须引入jar包 itext-asian-5.2.0.jar
                BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
//                String path = PDFUtil.class.getClassLoader().getResource("./dianziqianzhang/").getPath();
                BaseFont bf = BaseFont.createFont("/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font fontDetail = new Font(bf, pageFontSize, Font.NORMAL);
                pdfContent.setFontAndSize(baseFont, footerFontSize);
                // 页脚的页码 展示
                String footerNum = "";
//                if(writer.getCurrentPageNumber() > 3){
                    footerNum = String.format(String.format("第%d页", writer.getCurrentPageNumber())+"/共%d页",document.getPageNumber());
//                }

                Phrase phrase = new Phrase(footerNum, fontDetail);

                // 页码的 横轴 坐标 居中
                float x = ( document.left() + document.right() ) / 2 ;
                // 页码的 纵轴 坐标
                float y = document.bottom(-10) ;
                // 添加文本内容，进行展示页码
                ColumnText.showTextAligned(pdfContent, Element.ALIGN_CENTER, phrase, x, y, 0);

                pdfContent.endText();
                pdfContent.restoreState();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //pdf文件下载
    public static void pdfFileDownload(String PdfFilePath, HttpServletResponse response){

        try {
            File file = new File(PdfFilePath);
            response.setCharacterEncoding("utf-8");
            String fileName = file.getName();

            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            response.reset();
            if (fileType.equals("doc") || fileType.equals("docx") || fileType.equals("xls") || fileType.equals("xlsx")) {
                response.addHeader("content-type","application/x-msdownload");
            } else {
                response.addHeader("content-type","text/html");
            }
            response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
		/*如果文件长度大于0*/
            if (fileLength != 0) {
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
                inStream.close();
                servletOS.close();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    /**
     * pdf添加印章
     * @param urljPG
     * @param urlPdf
     */
    public static String addSignetForPdf(String urljPG, String urlPdf , int page , float percentX , float percentY , float percentW , float percentH  ) throws Exception {
            // 获取去除后缀的文件路径
        String fileName = urlPdf.substring(0, urlPdf.lastIndexOf("."));
        //把截取的路径，后面追加四位随机数
        String tempPdf = fileName + UUID.randomUUID() + ".PDF";

        File file = new File(urlPdf);
        file.renameTo(new File(tempPdf));//替换成新的pdf

        PdfReader reader = null;
        try {
            reader = new PdfReader(tempPdf, "PDF".getBytes());
            //加了水印后要输出的路径
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(urlPdf));
            Rectangle pageSize = reader.getPageSize(page);

            float height = pageSize.getHeight();//当前页码的高度
            float width = pageSize.getWidth();//当前页码的宽度

            // 插入水印
            Image img = Image.getInstance(Base64Utils.decodeFromString(urljPG));
            float a = width*percentW;
            float b = height*percentH;
            //印章位置
            img.setAbsolutePosition(percentX*width,  height-height*(percentY)-b);
            //印章大小
            img.scaleAbsolute(a,b);//长
            PdfContentByte under = stamp.getOverContent(page);//插入页
            //添加电子印章
            under.addImage(img);
            stamp.close();
            reader.close();
            // 关闭

            //关闭

        }  catch (Exception e) {
            throw new Exception("增加买受人盖章时出现错误:"+e.getMessage());
        }

        return tempPdf;

    }

    public static String addWaterForPdf(String urljPG, String urlPdf ) throws Exception {
        // 获取去除后缀的文件路径
        String fileName = urlPdf.substring(0, urlPdf.lastIndexOf("."));
        //把截取的路径，后面追加四位随机数
        String tempPdf = fileName + UUID.randomUUID() + ".pdf";

        File file = new File(urlPdf);
        file.renameTo(new File(tempPdf));//替换成新的pdf

        PdfReader reader = null;
        try {
            reader = new PdfReader(tempPdf, "PDF".getBytes());
            //加了水印后要输出的路径
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(urlPdf));
            int pageSizeToal = reader.getNumberOfPages();
            for(int i =1;i<=pageSizeToal;i++ ){
                Rectangle pageSize = reader.getPageSize(i);

                float height = pageSize.getHeight();//当前页码的宽度高度
                float width = pageSize.getWidth();//当前页码的宽度高度

                // 插入水印
                byte[] bytes = Base64Utils.decodeFromString(urljPG);
                Image img = Image.getInstance(bytes);

                //印章位置
                img.setAbsolutePosition(0, 0);
                //印章大小
                img.scaleAbsoluteHeight(height);//长
                img.scaleAbsoluteWidth(width);//宽
                PdfContentByte under = stamp.getUnderContent(i);

                //添加电子印章
                under.addImage(img);
            }
            stamp.close();
            reader.close();
            // 关闭

            //
            File file1 = new File(tempPdf);
            if(file1.exists()){
                file1.delete();
            }
        }  catch (Exception e) {
            throw new Exception("增加买受人盖章时出现错误"+e.getMessage());
        }

        return tempPdf;

    }

}


