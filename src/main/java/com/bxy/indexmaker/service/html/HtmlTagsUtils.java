package com.bxy.indexmaker.service.html;

import static com.bxy.indexmaker.service.FilePathService.getHtmlHomePath;

public class HtmlTagsUtils {

    public static final String CHAPITRE_1 = "bases";
    public static final String CHAPITRE_2 = "Parler";
    public static final String CHAPITRE_3 = "action sociale";
    public static final String CHAPITRE_4 = "Les aînée.e.s";
    public static final String DIV_ID_CH1 = "01-bases-";
    public static final String DIV_ID_CH2 = "02-parler-";
    public static final String DIV_ID_CH3 = "03-actionsoc-";
    public static final String CHAPITRE1_HTML = "chapitre1.html";
    public static final String CHAPITRE2_HTML = "chapitre2.html";
    public static final String CHAPITRE3_HTML = "chapitre3.html";
    public static final String CHAPITRE4_HTML = "chapitre4.html";
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

    public static String openingDivClassRowAlignCenter() {
        return "<div class=\"row\" align=\"center\" >";
    }

    public static String closingDiv() {
        return "</div>";
    }

    public static String openingParagraphClassLeadBlogDescription() {
        return "<p class=\"lead blog-description\">";
    }

    public static String getChapterStyle() {
        StringBuilder sb = new StringBuilder();
//        sb.append("        h2 {\n" +
//                "            background: url('images/header-test.png') no-repeat left top;\n" +
//                "            color: white;\n" +
//                "            /*width: 200px;*/\n" +
//                "            /*height: 50px;*/\n" +
//                "        }");
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

    public static String closingParagraph() {
        return "</p>";
    }

    public static String newLine() {
        return "<br />";
    }

    public static String openingDivClassColSm8BlogMain() {
        return "<div class=\"col-sm-8 blog-main\" >";
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

    public static String openingDivClassBlogPost() {
        return "<div class=\"blog-post\" >";
    }

    public static String openingParagraph() {
        return "<p>";
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

    public static String openingDivClassRow() {
        return "<div class=\"row\" >";
    }

    public static String h1Opening() {
        return "<h1>";
    }

    public static String h1Closing() {
        return "</h1>";
    }

    public static String h2Opening() {
        return "<h2>";
    }

    public static String subChapterOpeningDiv() {
        return new StringBuilder()
                .append(openingDivClassContainer())
                .append(openingDivClassRow())
                .append(openingDivClassColSm8BlogMain())
                .append(openingDivClassBlogPost())
                .toString();
    }

    public static String h1ClassBlogTitleOpening() {
        return "<h1 class=\"blog-title\">";
    }

    public static String subChapterClosingDiv() {
        return new StringBuilder()
                .append(closingDiv())
                .append(closingDiv())
                .append(closingDiv())
                .append(closingDiv())
                .toString();
    }
}
