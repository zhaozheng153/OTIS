import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class sqlserver {

    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/leadnews_admin";
        String url = "jdbc:sqlserver://10.7.30.66:1433;DatabaseName=origene_clones_mirror";
        Connection conn;
        try {
            new SimpleDateFormat("yyyy");
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,-7);
            System.out.println(instance.getTime());
            Date date = new Date();
            System.out.println(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR,-1);
            Date time = calendar.getTime();
            calendar.add(Calendar.YEAR,-1);
            Date time1 = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(date);

            System.out.println();

            System.out.println("开始时间:"+date+",结束时间:"+time+"，结束时间一Y："+time1);


            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, "sa", "origenesql5");
            System.out.println("连接成功");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }



}


/*

    */
/**
     * 创建PDF文件
     * @param htmlStr
     * @throws Exception
     *//*

    private static void writeToOutputStreamAsPDF(String htmlStr) throws Exception {
        String targetFile = "pdfDemo.pdf";
        File targeFile;
        targeFile = new File(targetFile);
        if(targeFile.exists()) {
            targeFile.delete();
        }

        //定义pdf文件尺寸，采用A4横切
        Document document = new Document(PageSize.A4, 25, 25, 15, 40);// 左、右、上、下间距
        //定义输出路径
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetFile));
//        PdfReportHeaderFooter header = new PdfReportHeaderFooter("", 8, PageSize.A4);
//        writer.setPageEvent(header);
        writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
        document.open();

        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssAppliers cssAppliers = new CssAppliersImpl(new XMLWorkerFontProvider(){

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
    }

    */
/**
     * 读取 HTML 文件
     * @return
     *//*

    private static String readHtmlFile() {
        StringBuffer textHtml = new StringBuffer();
        try {
            File file = new File("C:\\daima\\OTI\\OTIS\\OTIS-First-Module\\src\\main\\resources\\templates\\map.html");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                textHtml.append(tempString+"\n");
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return textHtml.toString();
    }


    public static void toPdf(String sHtml){
        try {
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("C:\\Windows\\Fonts\\simfang.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("C:\\Windows\\Fonts\\simsun.ttc",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            String sDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String sTime = new SimpleDateFormat("HHmmssSSS").format(new Date());

            //指定文件存放路径
            URL url = sqlserver.class.getResource("/");
            String path = url.toURI().getPath();
            String path1 = path.replace("WEB-INF/classes/", "");
            String s = path + sDate + "\\";

            File file = new File(s);
            if (!file.exists() && !file.isDirectory()){
                file.mkdirs();
            }

            String FileName = sDate + sTime + ".pdf";
            FileOutputStream stream = new FileOutputStream(FileName);

            renderer.setDocumentFromString(sHtml);
            renderer.layout();
            renderer.createPDF(stream);
            stream.close();


        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public static void main(String[] args) throws Exception {
        //读取html文件
        String htmlStr = readHtmlFile();
        System.out.println(htmlStr);
        //将html文件转成PDF
//        writeToOutputStreamAsPDF(htmlStr);

        toPdf(htmlStr);
    }

}
*/
