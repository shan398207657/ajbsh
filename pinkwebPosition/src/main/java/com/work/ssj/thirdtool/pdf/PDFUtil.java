package com.work.ssj.thirdtool.pdf;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public  class PDFUtil {

    public static void htmlToPdf(String htmlContent, String filePath, String fName )  throws Exception {

        FileUtils.forceMkdir(new File(filePath+"/1"+fName).getParentFile());
        try (OutputStream fileStream = new FileOutputStream(filePath+"/1"+fName)) {
            ITextRenderer textRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = textRenderer.getFontResolver();

            textRenderer.setDocumentFromString(htmlContent, null);
            fontResolver.addFont("/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("/fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            textRenderer.layout();
            textRenderer.createPDF(fileStream, true);
            PDFUtil.addPageNum(filePath+"/1"+fName,filePath+"/"+fName);
        }
    }

    //PDF文件添加页码
    //给PDF文件添加页码
    public static void addPageNum (String orgPdfPath, String outputPdfPath) throws IOException, DocumentException {
        // 输出文件流
        FileOutputStream fos = new FileOutputStream(outputPdfPath);
        // 新建文档，默认A4大小
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        // 设置页面监听事件，必须在open方法前
        writer.setPageEvent(new PageNumPdfPageEvent());
        document.open();
        // PDF内容体
        PdfContentByte pdfContent = writer.getDirectContent();
        // 读取 源PDF文件，进行一页一页复制，才能触发 添加页码的  页面监听事件
        PdfReader reader = new PdfReader(orgPdfPath);
        // 获取 源文件总页数
        int num = reader.getNumberOfPages();
        // 页面数是从1开始的
        for (int i = 1; i <= num; i++) {
            document.newPage();
            writer.setPageEmpty(false);
            document.setPageCount(num);
            PdfImportedPage page = writer.getImportedPage(reader, i);

            // 复制好的页面，添加到内容去，触发事件监听
            pdfContent.addTemplate(page, 0, 0);
        }
        document.close();
        reader.close();
        File file = new File(orgPdfPath);// 读取
        FileUtil.del(file);
        writer.close();
    }

    /**
     * 为PDF文件添加页码（已生成的pdf文件）
     */
    public static class PageNumPdfPageEvent extends PdfPageEventHelper{

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
                BaseFont bf = BaseFont.createFont("/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font fontDetail = new Font(bf, pageFontSize, Font.NORMAL);
                pdfContent.setFontAndSize(baseFont, footerFontSize);
                // 页脚的页码 展示
                String footerNum = String.format(String.format("第%d页", writer.getCurrentPageNumber())+"/共%d页",document.getPageNumber());

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
}


