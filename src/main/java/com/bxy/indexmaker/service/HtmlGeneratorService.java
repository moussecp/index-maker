package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;


@Service
public class HtmlGeneratorService {

    public static final String CHAPITRE_1 = "bases";
    public static final String CHAPITRE_2 = "Parler";
    public static final String CHAPITRE_3 = "action sociale";
    public static final String DIV_ID_CH1 = "01-bases-";
    public static final String DIV_ID_CH2 = "02-parler-";
    public static final String DIV_ID_CH3 = "03-actionsoc-";
    public static final String CHAPITRE1_HTML = "chapitre1.html";
    public static final String CHAPITRE2_HTML = "chapitre2.html";
    public static final String CHAPITRE3_HTML = "chapitre3.html";
    //    public static final String PATH = "D:\\\\Workspace\\\\index-maker\\\\src\\\\main\\\\resources\\\\html\\\\";
    public static final String PATH = "/home/tms/workspace/indexmaker/src/main/resources/html/";
    public static final String TEMPLATE_HTML = "template.html";
    public static final String TEMPLATE_PATH = PATH + TEMPLATE_HTML;
    public static final String N_A = "N/A";
    public static final String DIV_ID_CHAPTER = "-chapter";
    public static final String DIV_ID_SUB_CHAPTER = "-subChapter";
    public static final String DIV_ID_SECTION = "-section";
    public static final String DIV_ID_SUB_SECTION = "-subSection";
    public static final String DIV_ID_SUB_SUB_SECTION = "-subSubSection";
    private File htmlTemplateFile = new File(TEMPLATE_PATH);

    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    //TODO use real hibernate Repository
    private ReferenceRepository referenceRepository = new ReferenceMapDao();


    public void generateChapter1() throws IOException {
        List<RowContent> rowContents = getRowContentsFromChapter(CHAPITRE_1);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_1);

        String body = getChapterBody(rowContents, references, DIV_ID_CH1);
        String title = CHAPITRE_1;
        String outputFilePath = PATH + CHAPITRE1_HTML;
        generateHtmlFile(body, title, "", getFirstFiveReferences(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE1_HTML);
    }

    public void generateChapter2() throws IOException {
        List<RowContent> rowContents = getRowContentsFromChapter(CHAPITRE_2);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_2);

        String body = getChapterBody(rowContents, references, DIV_ID_CH2);
        String title = CHAPITRE_2;
        String outputFilePath = PATH + CHAPITRE2_HTML;
        generateHtmlFile(body, title, "", getFirstFiveReferences(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE2_HTML);
    }

    public void generateChapter3() throws IOException {
        List<RowContent> rowContents = getRowContentsFromSubChapter(CHAPITRE_3);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_3);

        String body = getChapterBody2(rowContents, references, DIV_ID_CH3);
        String style = getChapterStyle();
        String title = CHAPITRE_3;
        String outputFilePath = PATH + CHAPITRE3_HTML;
        generateHtmlFile(body, title, style, getFirstFiveReferences(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE3_HTML);
    }

    private String getFirstFiveReferences(List<Reference> references) {
        return getFirstReferences(references, 5);
    }

    private String getFirstReferences(List<Reference> references, long limitNumber) {
        return references.stream()
                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .limit(limitNumber)
                .map(e -> e.toString())
                .reduce("; ", String::concat);
    }

    private String getChapterBody(List<RowContent> rowContents, List<Reference> references, String divChapId) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int indexMax = rowContents.size();
        String previousChapter = EMPTY;
        String previousSubChapter = EMPTY;
        String previousSection = EMPTY;
        String previousSubSection = EMPTY;
        String previousSubSubSection = EMPTY;
        String currentChapter = EMPTY;
        String currentSubChapter = EMPTY;
        String currentSection = EMPTY;
        String currentSubSection = EMPTY;
        String currentSubSubSection = EMPTY;
        boolean isChapterDivOpen = false;
        boolean isSubChapterDivOpen = false;
        boolean isSectionDivOpen = false;
        boolean isSubSectionDivOpen = false;

        for (RowContent rowContent : rowContents) {
            currentChapter = rowContent.getChapter();
            currentSubChapter = rowContent.getSubChapter();
            currentSection = rowContent.getSection();
            currentSubSection = rowContent.getSubSection();
            currentSubSubSection = rowContent.getSubSubSection();
            String content = rowContent.getContent();

            String divId = divChapId + String.format("%05d", index);
            if (!currentChapter.equals(previousChapter)) {
                isChapterDivOpen = true;
                if (index != indexMax) {
                    appendOpeningDivWithId(sb, divId + DIV_ID_CHAPTER);
                    if (!currentChapter.equals(N_A)) {
                        appendHeading(sb, currentChapter, "h1");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!currentSubChapter.equals(previousSubChapter)) {
                isSubChapterDivOpen = true;
                if (index != indexMax) {
                    appendOpeningDivWithId(sb, divId + DIV_ID_SUB_CHAPTER);
                    if (!currentSubChapter.equals(N_A)) {
                        appendHeading(sb, currentSubChapter, "h2");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!currentSection.equals(previousSection)) {
                isSectionDivOpen = true;
                if (index != indexMax) {
                    appendOpeningDivWithId(sb, divId + DIV_ID_SECTION);
                    if (!currentSection.equals(N_A)) {
                        appendHeading(sb, currentSection, "h3");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!currentSubSection.equals(previousSubSection)) {
                isSubSectionDivOpen = true;
                if (index != indexMax) {
                    sb.append("<div class=\"list-group\" id=" + divId + DIV_ID_SUB_SECTION + ">");
                    if (!currentSubSection.equals(N_A)) {
                        appendHeading(sb, currentSubSection, "h4");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!isHeaderContent(currentChapter, currentSubChapter, currentSection, currentSubSection, content)) {
//            if(!currentSubSubSection.equals(previousSubSubSection)) {
//                if(index != indexMax) {
//                    sb.append("<a href=\"#\" class=\"list-group-item\" id="+divId+">");
//                }
//                if(index != 0) {
//                    sb.append("</a>");
//                }
//            }
                String modifiedContent = content.replaceAll("- ", "<br>- ");
                sb.append("<a href=\"#\" class=\"list-group-item\" id=" + divId + DIV_ID_SUB_SUB_SECTION + ">");
                sb.append(modifiedContent);
                sb.append("</a>");
            }
            index++;
            previousChapter = currentChapter;
            previousSubChapter = currentSubChapter;
            previousSection = currentSection;
            previousSubSection = currentSubSection;
            previousSubSubSection = currentSubSubSection;
        }
        if (isChapterDivOpen) appendClosingDiv(sb); //chapter
        if (isSubChapterDivOpen) appendClosingDiv(sb); //subchapter
        if (isSectionDivOpen) appendClosingDiv(sb); //section
        if (isSubSectionDivOpen) appendClosingDiv(sb); //subsection
        return sb.toString();
    }

    private String getChapterStyle() {
        StringBuilder sb = new StringBuilder();
        sb.append("        h2 {\n" +
                "            background: url('header-test.png') no-repeat left top;\n" +
                "            color: white;\n" +
                "            /*width: 200px;*/\n" +
                "            /*height: 50px;*/\n" +
                "        }");
        sb.append("        .text-block {\n" +
                "            position: absolute;\n" +
                "            top: 50%;\n" +
                "            left: 50%;\n" +
                "            /*transform: translate(-200%, -20%);*/\n" +
                "            background-color: black;\n" +
                "            color: white;\n" +
                "            padding-left: 20px;\n" +
                "            padding-right: 20px;\n" +
                "        }");
        return sb.toString();
    }


    //TODO COPY from https://v4-alpha.getbootstrap.com/examples/blog/
    private String getChapterBody2(List<RowContent> rowContents, List<Reference> references, String divChapId) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int indexMax = rowContents.size();
        String previousChapter = EMPTY;
        String previousSubChapter = EMPTY;
        String previousSection = EMPTY;
        String previousSubSection = EMPTY;
        String previousSubSubSection = EMPTY;
        String currentChapter = EMPTY;
        String currentSubChapter = EMPTY;
        String currentSection = EMPTY;
        String currentSubSection = EMPTY;
        String currentSubSubSection = EMPTY;
        boolean isChapterDivOpen = false;
        boolean isSubChapterDivOpen = false;
        boolean isSectionDivOpen = false;
        boolean isSubSectionDivOpen = false;

        for (RowContent rowContent : rowContents) {
            currentChapter = rowContent.getChapter();
            currentSubChapter = rowContent.getSubChapter();
            currentSection = rowContent.getSection();
            currentSubSection = rowContent.getSubSection();
            currentSubSubSection = rowContent.getSubSubSection();
            String content = rowContent.getContent();

//            String divId = divChapId + String.format("%05d", index);
            if (!currentChapter.equals(previousChapter)) {
                isChapterDivOpen = true;
                if (index != indexMax) {
                    appendOpeningHeaderDiv(sb);
                    if (!currentChapter.equals(N_A)) {
                        appendBlogTitleHeading(sb, currentChapter);
                    }
                }

                    appendClosingDiv(sb);

            }
            if (!currentSubChapter.equals(previousSubChapter)) {
                isSubChapterDivOpen = true;
                if (index != indexMax) {
                    appendOpeningDiv(sb);
                    if (!currentSubChapter.equals(N_A)) {
                        appendHeading(sb, currentSubChapter, "h2");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!currentSection.equals(previousSection)) {
                isSectionDivOpen = true;
                if (index != indexMax) {
                    String image = "forest01.jpg";
                    sb.append("<img src=\"" + image + "\" alt=\"" + currentSection + "\" style=\"width:100%;\">");
                    appendOpeningDivWithTextBlock(sb);
                    if (!currentSection.equals(N_A)) {
                        appendHeading(sb, currentSection, "h3");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!currentSubSection.equals(previousSubSection)) {
                isSubSectionDivOpen = true;
                if (index != indexMax) {
                    sb.append("<div class=\"list-group\" >");
                    if (!currentSubSection.equals(N_A)) {
                        appendHeading(sb, currentSubSection, "h4");
                    }
                }
                if (index != 0) {
                    appendClosingDiv(sb);
                }
            }
            if (!isHeaderContent(currentChapter, currentSubChapter, currentSection, currentSubSection, content)) {
//            if(!currentSubSubSection.equals(previousSubSubSection)) {
//                if(index != indexMax) {
//                    sb.append("<a href=\"#\" class=\"list-group-item\" id="+divId+">");
//                }
//                if(index != 0) {
//                    sb.append("</a>");
//                }
//            }
                String modifiedContent = content.replaceAll("- ", "<br>- ");
                sb.append("<p>");
                sb.append(modifiedContent);
                sb.append("</p>");
            }
            index++;
            previousChapter = currentChapter;
            previousSubChapter = currentSubChapter;
            previousSection = currentSection;
            previousSubSection = currentSubSection;
            previousSubSubSection = currentSubSubSection;
        }
        if (isChapterDivOpen) appendClosingDiv(sb); //chapter
        if (isSubChapterDivOpen) appendClosingDiv(sb); //subchapter
        if (isSectionDivOpen) appendClosingDiv(sb); //section
        if (isSubSectionDivOpen) appendClosingDiv(sb); //subsection
        return sb.toString();
    }

    private boolean isHeaderContent(String currentChapter, String currentSubChapter, String currentSection, String currentSubSection, String content) {
        return content.equals(currentChapter) || content.equals(currentSubChapter) || content.equals(currentSection) || content.equals(currentSubSection);
    }

    private void appendClosingDiv(StringBuilder sb) {
        sb.append("</div>");
    }

    private void appendBlogTitleHeading(StringBuilder sb, String headingTitle) {
        sb.append("<h1 class=\"blog-title\">" + headingTitle + "</h1>");
    }

    private void appendHeading(StringBuilder sb, String headingTitle, String heading) {
        sb.append("<" + heading + ">" + headingTitle + "</" + heading + ">");
    }

    private void appendOpeningDivWithId(StringBuilder sb, String divId) {
        sb.append("<div class=\"container-fluid\" id=" + divId + ">");
    }

    private void appendOpeningHeaderDiv(StringBuilder sb) {
        sb.append("<div class=\"blog-header\" >");
        sb.append("<div class=\"container\">");
    }

    private void appendOpeningDiv(StringBuilder sb) {
        sb.append("<div class=\"container-fluid\" >");
    }

    private void appendOpeningDivWithTextBlock(StringBuilder sb) {
        sb.append("<div class=\"text-block\" >");
    }

    private void generateHtmlFile(String body, String title, String style, String bodyIndex, String outputFilePath) throws IOException {
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        htmlString = htmlString.replace("$header-title", title);
        htmlString = htmlString.replace("$header-style", style);
        htmlString = htmlString.replace("$body-index", body);
        htmlString = htmlString.replace("$body-content", body);
        File newHtmlFile = new File(outputFilePath);
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    private List<RowContent> getRowContentsFromChapter(String chapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getChapter().contains(chapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

    private List<Reference> getReferencesFromChapterOrSubChapter(String chapter) {
        return referenceRepository.findAll()
                .stream()
                .filter(reference -> reference.getSubChaptersAsString().contains(chapter))
                .collect(Collectors.toList());
    }

    private List<RowContent> getRowContentsFromSubChapter(String subChapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getSubChapter().contains(subChapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

}
