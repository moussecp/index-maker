package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.RowContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.bxy.indexmaker.service.html.HtmlTagsUtils.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class HtmlHeaderMenuService {

    public static String getHeaderMenusAsHtml(List<RowContent> rowContents) {
        Set<HeaderMenu> headerMenus = getHeaderMenus(rowContents);
        StringBuilder sb = new StringBuilder();
        for (HeaderMenu headerMenu : headerMenus) {
            sb.append(openingListMenuItem())
                    .append(openingAHrefDivWithReference(headerMenu.getTagId()))
                    .append(headerMenu.getMenuTitle())
                    .append(closingAHref());
            if (headerMenu.hasSubMenus()) {
                sb.append(openingUnorderedListSubMenus());
                for (HeaderMenu subMenu : headerMenu.getSubMenus()) {
                    sb.append(openingListMenuItem())
                            .append(openingAHrefDivWithReference(subMenu.getTagId()))
                            .append(subMenu.getMenuTitle())
                            .append(closingAHref())
                            .append(closingListItem());
                }
                sb.append(closingUnorderedList());
            }
            sb.append(closingListItem());
        }
        return sb.toString();
    }

    public static Set<HeaderMenu> getHeaderMenus(List<RowContent> rowContents) {
        List<RowContent> rowContentsCopy = new ArrayList<>(rowContents);
        Set<HeaderMenu> headerMenus = new TreeSet<>();
        String previousChapter = EMPTY;
        String previousMenuTitle = EMPTY;
        String previousSubMenuTitle = EMPTY;
        HeaderMenu headerMenu = null;
        for (RowContent rowContent : rowContentsCopy) {
            String chapter = rowContent.getChapter();
            if(headerMenu != null && !previousChapter.equals(chapter)) {
                headerMenus.add(headerMenu);
                headerMenu = null;
            }
            String subChapter = rowContent.getSubChapter();
            String currentMenuTitle = rowContent.getChapter();
//            String currentMenuTitle = rowContent.getChapterPlusSubChapter();
            String currentSubMenuTitle = rowContent.getChapterPlusSubChapter();
//            String currentSubMenuTitle = rowContent.getChapterPlusSubChapterPlusSection();
//            if (!previousMenuTitle.equals(currentMenuTitle) && !HtmlTagsUtils.N_A.equals(subChapter)) {
            if (!previousMenuTitle.equals(currentMenuTitle) && !HtmlTagsUtils.N_A.equals(chapter)) {
                if (headerMenu != null) {
                    headerMenus.add(headerMenu);
                }
//                headerMenu = new HeaderMenu(currentMenuTitle, subChapter, rowContent.getFullHeadersId());
                headerMenu = new HeaderMenu(currentMenuTitle, chapter, rowContent.getFullHeadersId());
            }
            String section = rowContent.getSection();
//            if (headerMenu!= null && !previousSubMenuTitle.equals(currentSubMenuTitle) && !HtmlTagsUtils.N_A.equals(section)) {
            if (headerMenu!= null && !previousSubMenuTitle.equals(currentSubMenuTitle) && !HtmlTagsUtils.N_A.equals(subChapter)) {
//                headerMenu.getSubMenus().add(new HeaderMenu(currentMenuTitle, section, rowContent.getFullHeadersId()));
                headerMenu.getSubMenus().add(new HeaderMenu(currentMenuTitle, subChapter, rowContent.getFullHeadersId()));
            }
            previousMenuTitle = currentMenuTitle;
            previousSubMenuTitle = currentSubMenuTitle;
            previousChapter = chapter;
        }
        if (headerMenu != null) {
            headerMenus.add(headerMenu);
        }
        return headerMenus;
    }

    private static class HeaderMenu implements Comparable {
        private String mainTitle;
        private String menuTitle;
        private String tagId;
        private Set<HeaderMenu> subMenus = new TreeSet<>();

        public HeaderMenu(String mainTitle, String menuTitle, String tagId) {
            this.mainTitle = mainTitle;
            this.menuTitle = menuTitle;
            this.tagId = tagId;
        }

        public String getTagId() {
            return tagId;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public Set<HeaderMenu> getSubMenus() {
            return subMenus;
        }

        public boolean hasSubMenus() {
            return !subMenus.isEmpty();
        }

        @Override
        public String toString() {
            return "HeaderMenu{" +
                    "mainTitle='" + mainTitle + '\'' +
                    ", menuTitle='" + menuTitle + '\'' +
                    ", subMenus=" + subMenus +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            return this.toString().compareTo(o.toString());
        }
    }
}
