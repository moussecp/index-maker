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
        int openedDivs = 0;
        for (RowContent rowContent : rowContents) {
            boolean isNewChapter = false;
            boolean isNewSubChapter = false;
            boolean isNewSection = false;
            boolean isNewSubSection = false;
            StringBuilder sb = new StringBuilder();

            //build closing tabs
            //chapter
            String fullHeader = rowContent.getFullHeadersId();
            String chapter = rowContent.getChapter();
            if (!previousChapter.equals(chapter) && !N_A.equals(chapter)) {
                if (!previousChapter.equals(N_A)) {
                    sb.append(buildClosingChapterHeader());
                    openedDivs--;
                }
                isNewChapter = true;
            }
            //subChapter
            String subChapter = rowContent.getSubChapter();
            if (!previousSubChapter.equals(subChapter) || isNewChapter) {
                if (!previousSubChapter.equals(N_A)) {
                    sb.append(buildClosingSubChapterHeader());
                }
                isNewSubChapter = true;
            }
            //section
            String section = rowContent.getSection();
            if (!previousSection.equals(section) || isNewSubChapter) {
                if (!previousSection.equals(N_A)) {
                    sb.append(buildClosingSectionHeader());
                }
                isNewSection = true;
            }
            //subSection
            String subSection = rowContent.getSubSection();
            if (!previousSubSection.equals(subSection) || isNewSection) {
                if (!previousSubSection.equals(N_A)) {
                    sb.append(buildClosingSubSectionHeader());
                }
                isNewSubSection = true;
            }
            //subSubSection
            String subSubSection = rowContent.getSubSubSection();
            if (!previousSubSubSection.equals(subSubSection) || isNewSubSection) {
                if (!previousSubSubSection.equals(N_A)) {
                    sb.append(buildClosingSubSubSectionHeader());
                }
            }

            //build opening tags
            //
            if (!previousChapter.equals(chapter) && !N_A.equals(chapter)) {
                sb.append(buildChapterHeader(chapter));
            }
            previousChapter = chapter;
            //subChapter
            if ((!previousSubChapter.equals(subChapter) || isNewChapter) && !N_A.equals(subChapter)) {
                sb.append(buildSubChapterHeader(subChapter, fullHeader));
            }
            previousSubChapter = subChapter;
            //section
            if ((!previousSection.equals(section) || isNewSubChapter) && !N_A.equals(section)) {
                sb.append(buildSectionHeader(section, fullHeader));
            }
            previousSection = section;
            //subSection
            if ((!previousSubSection.equals(subSection) || isNewSection) && !N_A.equals(subSection)) {
                sb.append(buildSubSectionHeader(subSection));
            }
            previousSubSection = subSection;
            //subSubSection
            if ((!previousSubSubSection.equals(subSubSection) || isNewSubSection) && !N_A.equals(subSubSection)) {
                sb.append(buildSubSubSectionHeader(subSubSection));
            }
            previousSubSubSection = subSubSection;


            if (sb.length() > 0) {
                headersStructure.put(index, sb.toString());
            }
            index++;
        }

        headersStructure.put(index, (headersStructure.get(index) != null ? headersStructure.get(index) : EMPTY).concat(missingClosingDivs()));
        return headersStructure;
    }

    private static String missingClosingDivs() {
        return new StringBuilder()
                .append(buildClosingChapterHeader())
                .append(buildClosingSubChapterHeader())
                .append(buildClosingSectionHeader())
                .append(buildClosingSubSectionHeader())
                .append(buildClosingSubSubSectionHeader())
                .toString();
    }

    private static String buildChapterHeader(String chapter) {
        return new StringBuilder()
                .append(chapterOpeningDiv())
                .append(h1ClassBlogTitleOpening())
                .append(emptyIfNull(chapter))
                .append(h1Closing())
                .toString();
    }

    private static String buildClosingChapterHeader() {
        return new StringBuilder()
                .append(chapterClosingDiv())
//                .append(newLine())
                .toString();
    }

    private static String buildSubChapterHeader(String subChapter, String fullHeader) {
        return new StringBuilder()
                .append(chapterOpeningDivWithId(fullHeader))
                .append(h1Opening())
                .append(emptyIfNull(subChapter))
                .append(h1Closing())
                .toString();
    }

    private static String buildClosingSubChapterHeader() {
        return new StringBuilder()
                .append(chapterClosingDiv())
//                .append(newLine())
                .toString();
    }

    private static String buildSectionHeader(String section, String fullHeader) {
        return new StringBuilder()
                .append(subChapterOpeningDivWithId(fullHeader))
                .append(h2Opening())
                .append(emptyIfNull(section))
                .append(h2Closing())
                .toString();
    }

    private static String buildClosingSectionHeader() {
        return new StringBuilder()
                .append(subChapterClosingDiv())
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
                .toString();
    }

    private static String emptyIfNull(String text) {
        return text != null ? text : EMPTY;
    }

}
