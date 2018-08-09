package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bxy.indexmaker.service.FilePathService.getExportedHtmlFilePath;
import static com.bxy.indexmaker.service.FilePathService.getHtmlHomePath;
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
    public static final String TEMPLATE_HTML = "template.html";
    public static final String TEMPLATE_PATH = getHtmlHomePath() + TEMPLATE_HTML;
    public static final String N_A = "N/A";
    public static final String DIV_ID_CHAPTER = "-chapter";
    public static final String DIV_ID_SUB_CHAPTER = "-subChapter";
    public static final String DIV_ID_SECTION = "-section";
    public static final String DIV_ID_SUB_SECTION = "-subSection";
    public static final String DIV_ID_SUB_SUB_SECTION = "-subSubSection";
    public static final String HEADER_TITLE = "$header-title";
    public static final String HEADER_STYLE = "$header-style";
    public static final String BODY_INDEX = "$body-index";
    public static final String BODY_CONTENT = "$body-content";
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
        String outputFilePath = getExportedHtmlFilePath() + CHAPITRE1_HTML;
        generateHtmlFile(body, title, "", getIndex(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE1_HTML);
    }

    private String getIndex(List<Reference> references) {
        return new StringBuilder()
                .append(openingParagraphClassLeadBlogDescription())
                .append(getFirstFiveReferences(references))
                .append(closingParagraph())
                .toString();
    }

    public void generateChapter2() throws IOException {
        List<RowContent> rowContents = getRowContentsFromChapter(CHAPITRE_2);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_2);

//        String body = getChapterBody(rowContents, references, DIV_ID_CH2);
        String body = buildBody(rowContents);
        String title = CHAPITRE_2;
        String outputFilePath = getExportedHtmlFilePath() + CHAPITRE2_HTML;
        generateHtmlFile(body, title, "", getIndex(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE2_HTML);
    }

    public void generateChapter3() throws IOException {
        List<RowContent> rowContents = getRowContentsFromSubChapter(CHAPITRE_3);
        List<Reference> references = getReferencesFromChapterOrSubChapter(CHAPITRE_3);

        String body = buildBody(rowContents);//getChapterBody2(rowContents, references, DIV_ID_CH3);
        String style = getChapterStyle();
        String title = CHAPITRE_3;
        String outputFilePath = getExportedHtmlFilePath() + CHAPITRE3_HTML;
        generateHtmlFile(body, title, style, getIndex(references), outputFilePath);
        System.out.println("html generated: " + CHAPITRE3_HTML);
    }

    protected String getFirstFiveReferences(List<Reference> references) {
        return getFirstReferences(references, 5);
    }

    public static String h1ClassBlogTitleOpening() {
        return "<h1 class=\"blog-title\">";
    }

    protected String getChapterStyle() {
        StringBuilder sb = new StringBuilder();
        sb.append("        h2 {\n" +
                "            background: url('images/header-test.png') no-repeat left top;\n" +
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

    public static String h1Opening() {
        return "<h1>";
    }

    public static String h1Closing() {
        return "</h1>";
    }

    public static String h2Opening() {
        return "<h2>";
    }

    protected boolean isHeaderContent(String currentChapter, String currentSubChapter, String currentSection, String currentSubSection, String content) {
        return content.equals(currentChapter) || content.equals(currentSubChapter) || content.equals(currentSection) || content.equals(currentSubSection);
    }

    public static String h2Closing() {
        return "</h2>";
    }

    public static String h3Opening() {
        return "<h3>";
    }

    public static String h3Closing() {
        return "</h3>";
    }

    public static String h4Opening() {
        return "<h4>";
    }

    public static String h4Closing() {
        return "</h4>";
    }

    public static String chapterOpeningDiv() {
        return new StringBuilder()
                .append(openingDivClassBlogHeader())
                .append(openingDivClassContainer())
                .toString();
    }

    public static String chapterClosingDiv() {
        return new StringBuilder()
                .append(closingDiv())
                .append(closingDiv())
                .toString();
    }

    public static String openingDivClassContainer() {
        return "<div class=\"container\">";
    }

    public static String openingDivClassBlogHeader() {
        return "<div class=\"blog-header\" >";
    }

    public static String openingDivClassContainerFluid() {
        return "<div class=\"container-fluid\" >";
    }

    public static String openingDivClassTextBlock() {
        return "<div class=\"text-block\" >";
    }

    public static String closingDiv() {
        return "</div>";
    }

    public static String openingParagraphClassLeadBlogDescription() {
        return "<p class=\"lead blog-description\">";
    }

    public static String closingParagraph() {
        return "</p>";
    }

    protected String getFirstReferences(List<Reference> references, long limitNumber) {
        StringBuilder sb = new StringBuilder();
        Optional<String> optionalReferences = references
                .stream()
                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .limit(limitNumber)
                .map(Reference::getWord)
                .map(e -> e.concat(" ; "))
                .reduce(String::concat);
        if (!optionalReferences.isPresent()) {
            return EMPTY;
        }
        sb.append(optionalReferences.get())
                .deleteCharAt(sb.length() - 1)
                .deleteCharAt(sb.length() - 1)
                .deleteCharAt(sb.length() - 1)
        ;
        return sb.toString();
    }

    protected String buildBody(List<RowContent> rowContents) {
        StringBuilder sb = new StringBuilder();
        int indexChapter = 0;
        for (RowContent rowContent : rowContents) {
            String chapter = buildChapter(rowContents, indexChapter);
            sb.append(chapter != null ? chapter : EMPTY);
            indexChapter++;
        }
        return sb.length() > 0 ? sb.toString() : EMPTY;
    }

    protected String buildChapter(List<RowContent> rowContents, int indexChapter) {
        StringBuilder sb = new StringBuilder();
        String previousChapter = indexChapter != 0 ? rowContents.get(indexChapter - 1).getChapter() : EMPTY;
        String currentChapter = rowContents.get(indexChapter).getChapter();
        boolean isNewChapter = !currentChapter.equals(previousChapter);
        boolean isSubChapterPresent = !rowContents.get(indexChapter).getSubChapter().equals(N_A);
        StringBuilder sbContentWithoutSubChapter = new StringBuilder();
        int indexSubChapter = indexChapter;

        indexSubChapter = buildContentWithoutSubHeader(rowContents, isSubChapterPresent, sbContentWithoutSubChapter, indexSubChapter);

        if (isNewChapter) {
            sb.append(chapterOpeningDiv());
            if (!currentChapter.equals(N_A)) {
                sb.append(h1ClassBlogTitleOpening());
                sb.append(currentChapter);
                sb.append(h1Closing());
                sb.append(sbContentWithoutSubChapter);
            }
            sb.append(chapterClosingDiv());
        }
        sb.append(buildSubChapter(rowContents, indexSubChapter));
        return sb.length() > 0 ? sb.toString() : EMPTY;
    }

    protected String buildSubChapter(List<RowContent> rowContents, int indexSubChapter) {
        StringBuilder sb = new StringBuilder();
        String previousSubChapter = indexSubChapter != 0 ? rowContents.get(indexSubChapter - 1).getSubChapter() : EMPTY;
        String currentSubChapter = rowContents.get(indexSubChapter).getSubChapter();
        boolean isNewSubChapter = !currentSubChapter.equals(previousSubChapter);
        boolean isSectionPresent = !rowContents.get(indexSubChapter).getSection().equals(N_A);
        StringBuilder sbContentWithoutSection = new StringBuilder();
        int indexSection = indexSubChapter;

//        indexSection = buildContentWithoutSubHeader(rowContents, isSectionPresent, sbContentWithoutSection, indexSection);

        if (isNewSubChapter) {
            sb.append(openingParagraphClassLeadBlogDescription());
            if (!currentSubChapter.equals(N_A)) {
                sb.append(h2Opening());
                sb.append(currentSubChapter);
                sb.append(h2Closing());
//                sb.append(sbContentWithoutSection);
            }
            sb.append(closingParagraph());
        }
        sb.append(buildSection(rowContents, indexSection));
        return sb.length() > 0 ? sb.toString() : EMPTY;
    }

    private int buildContentWithoutSubHeader(List<RowContent> rowContents, boolean isSubHeaderPresent, StringBuilder sbContentWithoutSubHeader, int indexSubHeader) {
        if (!isSubHeaderPresent) {
            String currentHeaders = rowContents.get(indexSubHeader).getHeaders();
            while (indexSubHeader < rowContents.size() && currentHeaders.equals(rowContents.get(indexSubHeader).getHeaders())) {
                sbContentWithoutSubHeader.append(rowContents.get(indexSubHeader).getContent());
                ++indexSubHeader;
            }
        }
        return indexSubHeader;
    }

    private String buildSection(List<RowContent> rowContents, int indexSection) {
        return rowContents.get(indexSection).getContent();
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
                    sb.append(h1ClassBlogTitleOpening());
                    sb.append(currentChapter);
                    sb.append(h1Closing());
//                            <p class="lead blog-description">An example blog template built with Bootstrap.</p>
                }
                chapterClosingDiv();
            }

            if (isNewSubChapter) {
                isSubChapterDivOpen = true;
                if (index != indexMax) {
                    openingDivClassContainerFluid();
                    if (!currentSubChapter.equals(N_A)) {
                        sb.append(h2Opening());
                        sb.append(currentSubChapter);
                        sb.append(h2Closing());
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
                        sb.append(h3Opening());
                        sb.append(currentSection);
                        sb.append(h3Closing());
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
                        sb.append(h4Opening());
                        sb.append(currentSubSection);
                        sb.append(h4Closing());
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

    protected void generateHtmlFile(String body, String title, String style, String bodyIndex, String outputFilePath) throws IOException {
        String htmlString = generateHtmlString(body, title, style, bodyIndex);
        File newHtmlFile = new File(outputFilePath);
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    protected String generateHtmlString(String body, String title, String style, String bodyIndex) throws IOException {
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        htmlString = htmlString.replace(HEADER_TITLE, title);
        htmlString = htmlString.replace(HEADER_STYLE, style);
        htmlString = htmlString.replace(BODY_INDEX, bodyIndex);
        htmlString = htmlString.replace(BODY_CONTENT, body);
        return htmlString;
    }

    protected List<RowContent> getRowContentsFromChapter(String chapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getChapter().contains(chapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

    protected List<Reference> getReferencesFromChapterOrSubChapter(String chapter) {
        return referenceRepository.findAll()
                .stream()
                .filter(reference -> reference.getSubChaptersAsString().contains(chapter))
                .collect(Collectors.toList());
    }

    protected List<RowContent> getRowContentsFromSubChapter(String subChapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getSubChapter().contains(subChapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

}
