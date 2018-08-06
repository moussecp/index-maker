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
    public static final String PATH = "D:\\\\Workspace\\\\index-maker\\\\src\\\\main\\\\resources\\\\html\\\\";
    //    public static final String PATH = "/home/tms/workspace/indexmaker/src/main/resources/html/";
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

//        String body = getChapterBody(rowContents, references, DIV_ID_CH1);
        String body = buildBody(rowContents);
        String title = CHAPITRE_1;
        String outputFilePath = PATH + CHAPITRE1_HTML;
        generateHtmlFile(body, title, "", getFirstFiveReferences(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE1_HTML);
    }

    public void generateChapter2() throws IOException {
        List<RowContent> rowContents = getRowContentsFromChapter(CHAPITRE_2);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_2);

//        String body = getChapterBody(rowContents, references, DIV_ID_CH2);
        String body = buildBody(rowContents);
        String title = CHAPITRE_2;
        String outputFilePath = PATH + CHAPITRE2_HTML;
        generateHtmlFile(body, title, "", getFirstFiveReferences(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE2_HTML);
    }

    public void generateChapter3() throws IOException {
        List<RowContent> rowContents = getRowContentsFromSubChapter(CHAPITRE_3);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_3);

        String body = buildBody(rowContents);//getChapterBody2(rowContents, references, DIV_ID_CH3);
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
//                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .limit(limitNumber)
                .map(e -> e.toString())
                .reduce("; ", String::concat);
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

    private String buildBody(List<RowContent> rowContents) {
        StringBuilder sb = new StringBuilder();
        int indexChapter = 0;
        for (RowContent rowContent : rowContents) {
            sb.append(buildChapter(rowContents, indexChapter));
            indexChapter++;
        }
        return sb.toString();
    }

    private String buildChapter(List<RowContent> rowContents, int indexChapter) {
        StringBuilder sb = new StringBuilder();
        String previousChapter = indexChapter != 0 ? rowContents.get(indexChapter - 1).getChapter() : EMPTY;
        String currentChapter = rowContents.get(indexChapter).getChapter();
        boolean isNewChapter = !currentChapter.equals(previousChapter);
        boolean isSubChapterPresent = !rowContents.get(indexChapter).getSubChapter().equals(N_A);
        StringBuilder sbContentWithoutSubChapter = new StringBuilder();
        int indexSubChapter = indexChapter;

        if (!isSubChapterPresent) {
            String currentHeaders = rowContents.get(indexSubChapter).getHeaders();
            while (rowContents.size() < indexSubChapter && currentHeaders.equals(rowContents.get(indexSubChapter).getHeaders())) {
                ++indexSubChapter;
                sbContentWithoutSubChapter.append(rowContents.get(indexSubChapter).getContent());
            }
        }

        if (isNewChapter) {
            sb.append(chapterOpeningDiv());
            if (!currentChapter.equals(N_A)) {
                sb.append(h1ClassBlogTitle(currentChapter));
                sb.append(sbContentWithoutSubChapter);
            }
            sb.append(chapterClosingDiv());
        }
        sb.append(buildSubChapter(rowContents, indexSubChapter));
        return sb.toString();

    }

    private String buildSubChapter(List<RowContent> rowContents, int indexSubChapter) {
        return EMPTY;
    }


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
            boolean isNewChapter = !currentChapter.equals(previousChapter);
            boolean isNewSubChapter = !currentSubChapter.equals(previousSubChapter);
            boolean isNewSection = !currentSection.equals(previousSection);
            boolean isNewSubSection = !currentSubSection.equals(previousSubSection);

//            String divId = divChapId + String.format("%05d", index);
            if (isNewChapter) {
                chapterOpeningDiv();
                if (!currentChapter.equals(N_A)) {
                    h1ClassBlogTitle(currentChapter);
//                            <p class="lead blog-description">An example blog template built with Bootstrap.</p>
                }
                chapterClosingDiv();
            }

            if (isNewSubChapter) {
                isSubChapterDivOpen = true;
                if (index != indexMax) {
                    openingDivClassContainerFluid();
                    if (!currentSubChapter.equals(N_A)) {
                        heading(currentSubChapter, "h2");
                    }
                }
                if (index != 0) {
                    closingDiv();
                }
            }
            if (isNewSection) {
                isSectionDivOpen = true;
                if (index != indexMax) {
                    String image = "forest01.jpg";
                    sb.append("<img src=\"" + image + "\" alt=\"" + currentSection + "\" style=\"width:100%;\">");
                    openingDivClassTextBlock();
                    if (!currentSection.equals(N_A)) {
                        heading(currentSection, "h3");
                    }
                }
                if (index != 0) {
                    closingDiv();
                }
            }
            if (isNewSubSection) {
                isSubSectionDivOpen = true;
                if (index != indexMax) {
                    sb.append("<div class=\"list-group\" >");
                    if (!currentSubSection.equals(N_A)) {
                        heading(currentSubSection, "h4");
                    }
                }
                if (index != 0) {
                    closingDiv();
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
        if (isSubChapterDivOpen) closingDiv(); //subchapter
        if (isSectionDivOpen) closingDiv(); //section
        if (isSubSectionDivOpen) closingDiv(); //subsection
        return sb.toString();
    }

    private boolean isHeaderContent(String currentChapter, String currentSubChapter, String currentSection, String currentSubSection, String content) {
        return content.equals(currentChapter) || content.equals(currentSubChapter) || content.equals(currentSection) || content.equals(currentSubSection);
    }

    private String h1ClassBlogTitle(String headingTitle) {
        return "<h1 class=\"blog-title\">" + headingTitle + "</h1>";
    }

    private String heading(String headingTitle, String heading) {
        return "<" + heading + ">" + headingTitle + "</" + heading + ">";
    }

    private String chapterOpeningDiv() {
        return new StringBuilder()
                .append(openingDivClassBlogHeader())
                .append(openingDivClassContainer())
                .toString();
    }

    private String chapterClosingDiv() {
        return new StringBuilder()
                .append(closingDiv())
                .append(closingDiv())
                .toString();
    }

    private String openingDivClassContainer() {
        return "<div class=\"container\">";
    }

    private String openingDivClassBlogHeader() {
        return "<div class=\"blog-header\" >";
    }


    private String openingDivClassContainerFluid() {
        return "<div class=\"container-fluid\" >";
    }

    private String openingDivClassTextBlock() {
        return "<div class=\"text-block\" >";
    }

    private String closingDiv() {
        return "</div>";
    }

    private String openingParagraphClassLeadBlogDescription() {
        return "<p class=\"lead blog-description\">";
    }

    private String closingParagraph() {
        return "</p>";
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
