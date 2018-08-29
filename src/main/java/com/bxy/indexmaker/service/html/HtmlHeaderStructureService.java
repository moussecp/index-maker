package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.RowContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bxy.indexmaker.service.html.HtmlTagsUtils.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class HtmlHeaderStructureService {

    public static Map<Long, String> buildHeadersStructure(List<RowContent> rowContents) {
        Map<Long, String> headersStructure = new HashMap<>();
        String previousChapter = N_A;
        String previousSubChapter = N_A;
        String previousSection = N_A;
        String previousSubSection = N_A;
        String previousSubSubSection = N_A;
        Long index = 0L;
        for (RowContent rowContent : rowContents) {
            StringBuilder sb = new StringBuilder();
            //chapter
            String chapter = rowContent.getChapter();
            if (!previousChapter.equals(chapter) && !N_A.equals(chapter)) {
                if (!previousChapter.equals(N_A)) {
                    sb.append(buildClosingChapterHeader());
                }
                sb.append(buildChapterHeader(chapter));
            }
            previousChapter = chapter;
            //subChapter
            String subChapter = rowContent.getSubChapter();
            if (!previousSubChapter.equals(subChapter) && !N_A.equals(subChapter)) {
                if (!previousSubChapter.equals(N_A)) {
                    sb.append(buildClosingSubChapterHeader());
                }
                sb.append(buildSubChapterHeader(subChapter));
            }
            previousSubChapter = subChapter;
            //section
            String section = rowContent.getSection();
            if (!previousSection.equals(section) && !N_A.equals(section)) {
                if (!previousSection.equals(N_A)) {
                    sb.append(buildClosingSectionHeader());
                }
                sb.append(buildSectionHeader(section));
            }
            previousSection = section;
            //subSection
            String subSection = rowContent.getSubSection();
            if (!previousSubSection.equals(subSection) && !N_A.equals(subSection)) {
                if (!previousSubSection.equals(N_A)) {
                    sb.append(buildClosingSubSectionHeader());
                }
                sb.append(buildSubSectionHeader(subSection));
            }
            previousSubSection = subSection;
            //subSubSection
            String subSubSection = rowContent.getSubSubSection();
            if (!previousSubSubSection.equals(subSubSection) && !N_A.equals(subSubSection)) {
                if (!previousSubSubSection.equals(N_A)) {
                    sb.append(buildClosingSubSubSectionHeader());
                }
                sb.append(buildSubSubSectionHeader(subSubSection));
            }
            previousSubSubSection = subSubSection;
            if (sb.length() > 0) {
                headersStructure.put(index, sb.toString());
            }
            index++;
        }
        return headersStructure;
    }

    private static String buildChapterHeader(String chapter) {
        return new StringBuilder()
                .append(chapterOpeningDiv())
                .append(h1ClassBlogTitleOpening())
                .append(emptyIfNull(chapter))
                .append(h1Closing())
                .append(newLine())
                .toString();
    }

    private static String buildClosingChapterHeader() {
        return new StringBuilder()
                .append(chapterClosingDiv())
                .append(newLine())
                .toString();
    }

    private static String buildSubChapterHeader(String subChapter) {
        return new StringBuilder()
                .append(chapterOpeningDiv())
                .append(h1ClassBlogTitleOpening())
                .append(emptyIfNull(subChapter))
                .append(h1Closing())
                .toString();
    }

    private static String buildClosingSubChapterHeader() {
        return new StringBuilder()
                .append(chapterClosingDiv())
                .append(newLine())
                .toString();
    }

    private static String buildSectionHeader(String section) {
        return new StringBuilder()
                .append(subChapterOpeningDiv())
                .append(h2Opening())
                .append(emptyIfNull(section))
                .append(h2Closing())
                .toString();
    }

    private static String buildClosingSectionHeader() {
        return new StringBuilder()
                .append(subChapterClosingDiv())
                .append(newLine())
                .toString();
    }

    private static String buildSubSectionHeader(String subSection) {
        return new StringBuilder()
                .append(subChapterOpeningDiv())
                .append(h3Opening())
                .append(emptyIfNull(subSection))
                .append(h3Closing())
                .toString();
    }

    private static String buildClosingSubSectionHeader() {
        return new StringBuilder()
                .append(subChapterClosingDiv())
                .append(newLine())
                .toString();
    }

    private static String buildSubSubSectionHeader(String subSubSection) {
        return new StringBuilder()
                .append(subChapterOpeningDiv())
                .append(h4Opening())
                .append(emptyIfNull(subSubSection))
                .append(h4Closing())
                .toString();
    }

    private static String buildClosingSubSubSectionHeader() {
        return new StringBuilder()
                .append(subChapterClosingDiv())
                .append(newLine())
                .toString();
    }

    private static String emptyIfNull(String text) {
        return text != null ? text : EMPTY;
    }

}
