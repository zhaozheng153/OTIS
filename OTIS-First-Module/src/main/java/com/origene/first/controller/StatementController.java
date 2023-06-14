package com.origene.first.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("Statement")
public class StatementController {

    @RequestMapping("start")
    public String start(){
        return "map";
    }

    @RequestMapping("outPDF")
    public void outPDF(HttpServletResponse response){

        try {

            Document document = new Document();
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font1 = new Font(font, 12, Font.NORMAL);
            Paragraph elements = new Paragraph("世界在向你求救", font1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            PdfWriter.getInstance(document,stream);

            document.open();

            document.add(elements);

            document.close();


            DataOutputStream dataOutputStream = new DataOutputStream(response.getOutputStream());
            byte[] bytes = stream.toByteArray();
            response.setContentLength(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                dataOutputStream.writeByte(bytes[i]);
            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @RequestMapping("outPDF1")
    public void outPDF1(HttpServletResponse response){
        StringBuffer textHtml = new StringBuffer();

        try {
            File file = new File("C:\\daima\\OTI\\OTIS\\OTIS-First-Module\\src\\main\\resources\\templates\\map.html");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str = null ;
            while ((str = reader.readLine()) != null){
                textHtml.append(str);
            }
            reader.close();

            String htmlStr = textHtml.toString();

            File targetFile = new File("pdfDemo.pdf");
            if (targetFile.exists()){
                targetFile.delete();
            }

            Document document = new Document(PageSize.A4, 25, 25, 15, 40);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetFile));
            writer.addViewerPreference(PdfName.PRINTSCALING,PdfName.NONE);
            document.open();

            //CSS
            StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver();
            CssAppliersImpl cssAppliers = new CssAppliersImpl(new XMLWorkerFontProvider() {
                @Override
                public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
                    try {
                        //用于中文显示的Provider
                        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                        return new Font(bfChinese, size, style);
                    } catch (Exception e) {
                        return super.getFont(fontname, encoding, size, style);
                    }
                }
            });

            //html
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new AbstractImageProvider() {
                @Override
                public Image retrieve(String src) {
                    //支持图片显示
                    int pos = src.indexOf("base64,");
                    try {
                        if (src.startsWith("data") && pos > 0) {
                            byte[] img = Base64.decode(src.substring(pos + 7));
                            return Image.getInstance(img);
                        } else if (src.startsWith("http")) {
                            return Image.getInstance(src);
                        }
                    } catch (BadElementException ex) {
                        return null;
                    } catch (IOException ex) {
                        return null;
                    }
                    return null;
                }

                @Override
                public String getImageRootPath() {
                    return null;
                }
            });

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            p.parse(new ByteArrayInputStream(htmlStr.getBytes()));

            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
