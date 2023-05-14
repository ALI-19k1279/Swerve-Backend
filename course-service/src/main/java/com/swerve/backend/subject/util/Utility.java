package com.swerve.backend.subject.util;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.MediaType;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Utility {

     private static Map<String,MediaType> fileExtensionToMediaMapper=new HashMap<String,MediaType>() {{
        put("pdf", MediaType.APPLICATION_PDF);
        put("jpeg", MediaType.IMAGE_JPEG);
        put("jpg", MediaType.IMAGE_JPEG);
        put("png", MediaType.IMAGE_PNG);
        put("txt", MediaType.TEXT_PLAIN);
        put("html", MediaType.TEXT_HTML);
    }};
    public static Double roundToTwoDecimals(Double d) {
        return Math.round(d * 100.0) / 100.0;
    }
    public static String getFileExtension(String filename) {
        if(filename == null || filename.isEmpty()) {
            return "";
        }
        int dotIndex = filename.lastIndexOf(".");
        if(dotIndex == -1 || dotIndex == 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }

    public static MediaType getMediaTypeForFileName(String fileName) {
        String extension = getFileExtension(fileName);
        return fileExtensionToMediaMapper.get(extension)!=null ? fileExtensionToMediaMapper.get(extension):MediaType.APPLICATION_OCTET_STREAM;
    }
    public static String convertDateFormat(Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(inputDate);
    }

    public static int getMonthNumber(String monthName) {
        // Map month names to month numbers
        switch (monthName) {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: return -1;
        }
    }

    public static byte[] generateDoc(String content) {
        try (XWPFDocument document = new XWPFDocument()) {
            Document htmlDoc = Jsoup.parse(content);
            Elements elements = htmlDoc.body().children();
            for (Element element : elements) {
                processContent(element, document);
            }

            // Generate the document bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static void processContent(Element element, XWPFDocument document) {
        switch (element.tagName()) {
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
                processHeading(element, document);
                break;
            case "p":
                processParagraph(element, document);
                break;
            case "code":
                processCodeBlock(element, document);
                break;
            case "blockquote":
                processBlockquote(element, document);
                break;
            case "ul":
                processBulletList(element, document);
                break;
            case "ol":
                processNumberedList(element, document);
                break;
            default:
                // Ignore unrecognized tags
                break;
        }
    }

    public static void processParagraph(Element element, XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        processInlineStyles(element, paragraph);
        XWPFRun run = paragraph.createRun();
        run.setText(element.text());
    }

    public static void processInlineStyles(Element element, XWPFParagraph paragraph) {
        if (element.tagName().equals("em")) {
            applyItalicFormatting(paragraph);
        } else if (element.tagName().equals("strong")) {
            applyBoldFormatting(paragraph);
        } else if (element.tagName().equals("u")) {
            applyUnderlineFormatting(paragraph);
        } else if (element.tagName().equals("code")) {
            applyCodeFormatting(paragraph);
        } else if (element.hasAttr("style")) {
            String style = element.attr("style");
            if (style.contains("text-align:")) {
                String alignment = style.substring(style.indexOf("text-align:") + 11).trim();
                applyAlignment(paragraph, alignment);
            }
        }
    }

    public static void applyItalicFormatting(XWPFParagraph paragraph) {
        for (XWPFRun run : paragraph.getRuns()) {
            run.setItalic(true);
        }
    }

    public static void applyBoldFormatting(XWPFParagraph paragraph) {
        for (XWPFRun run : paragraph.getRuns()) {
            run.setBold(true);
        }
    }

    public static void applyUnderlineFormatting(XWPFParagraph paragraph) {
        for (XWPFRun run : paragraph.getRuns()) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
    }

    public static void applyCodeFormatting(XWPFParagraph paragraph) {
        for (XWPFRun run : paragraph.getRuns()) {
            run.setFontFamily("Courier New");
            run.setFontSize(9);
            run.setColor("000000");
            run.setItalic(true);
        }
    }

    public static void applyAlignment(XWPFParagraph paragraph, String alignment) {
        switch (alignment) {
            case "left":
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                break;
            case "right":
                paragraph.setAlignment(ParagraphAlignment.RIGHT);
                break;
            case "center":
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                break;
            case "justify":
                paragraph.setAlignment(ParagraphAlignment.BOTH);
                break;
            default:
                // Ignore unrecognized alignment values
                break;
        }
    }

    public static void processCodeBlock(Element element, XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        processInlineStyles(element, paragraph);
        XWPFRun run = paragraph.createRun();
        run.setText(element.select("code").text());
        run.setFontFamily("Courier New");
        run.setFontSize(9);
        run.setColor("000000");
    }

    public static void processBlockquote(Element element, XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(element.text());
        run.setItalic(true);
        run.setColor("808080");
    }

    public static void processBulletList(Element element, XWPFDocument document) {
        for (Element li : element.select("li")) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("\u2022 " + li.text());
            run.setFontFamily("Calibri"); // Set the font family for bullet points
        }
    }
    public static void processHeading(Element element, XWPFDocument document) {
        String tagName = element.tagName();
        int level = Integer.parseInt(tagName.substring(1));
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setStyle("Heading" + level);
        XWPFRun run = paragraph.createRun();
        run.setText(element.text());

        // Set heading properties
        run.setBold(true);
        run.setFontSize(getHeadingSize(level));
    }

    public static int getHeadingSize(int level) {
        switch (level) {
            case 1:
                return 24;  // Heading 1 size
            case 2:
                return 18;  // Heading 2 size
            case 3:
                return 16;  // Heading 3 size
            case 4:
                return 14;  // Heading 4 size
            case 5:
                return 12;  // Heading 5 size
            case 6:
                return 10;  // Heading 6 size
            default:
                return 12;  // Default size
        }
    }
//    public static String saveUploadedFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//        String filePath = "classpath:static/uploads/" + fileName;
//        File destinationFile = ResourceUtils.getFile(filePath);
//        file.transferTo(destinationFile);
//        return destinationFile.getAbsolutePath();
//    }
    public static String saveUploadedFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path uploadPath = Paths.get("classpath:static/uploads/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            // Handle the exception appropriately
            throw new IOException("Failed to save the file: " + fileName, e);
        }
    }

    public static void processNumberedList(Element element, XWPFDocument document) {
        int index = 1;
        for (Element li : element.select("li")) {
            XWPFParagraph listItemParagraph = document.createParagraph();
            XWPFRun listItemRun = listItemParagraph.createRun();
            listItemRun.setText(index + ". ");
            listItemRun.setFontFamily("Calibri"); // Set the font family for ordered list
            listItemRun.setFontSize(9);
            listItemRun.setColor("000000");

            // Process the sub-numbered list recursively
            Elements subLists = li.select("ol");
            for (Element subList : subLists) {
                processNumberedList(subList, document);
            }

            // Process the list item text
            XWPFRun listItemTextRun = listItemParagraph.createRun();
            listItemTextRun.setText(li.text());
            listItemTextRun.setFontFamily("Calibri");

            index++;
        }
    }



    public static XWPFParagraph createNewParagraph(XWPFParagraph currentParagraph) {
        XWPFParagraph newParagraph = currentParagraph.getDocument().createParagraph();
        newParagraph.setStyle(currentParagraph.getStyle());
        return newParagraph;
    }


}
