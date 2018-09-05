package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.RowContent;

import java.util.*;

import static com.bxy.indexmaker.service.html.HtmlTagsUtils.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class HtmlHeaderMenuService {

    public static String getHeaderMenusAsHtml(List<RowContent> rowContents) {
        Set<HeaderMenu> headerMenus = getHeaderMenus(rowContents);
        StringBuilder sb = new StringBuilder();
        for (HeaderMenu headerMenu : headerMenus) {

            //TODO for each header, add chapter tag && add following subheader tags
            sb.append(openingListMenuItem())
                    .append(openingAHrefDivWithReference("test"))
                    .append(headerMenu)
                    .append(closingAHref())
                    .append(closingList());
        }
        return sb.toString();
    }

    public static Set<HeaderMenu> getHeaderMenus(List<RowContent> rowContents) {
        List<RowContent> rowContentsCopy = new ArrayList<>(rowContents);
        Set<HeaderMenu> headerMenus = new HashSet<>();
        Collections.sort(rowContentsCopy);
        String previousMenuTitle = EMPTY;
        String previousSubMenuTitle = EMPTY;
        HeaderMenu headerMenu = null;
        for(RowContent rowContent : rowContentsCopy) {
            String currentMenuTitle = rowContent.getChapter();
            if(!previousMenuTitle.equals(currentMenuTitle) && !HtmlTagsUtils.N_A.equals(currentMenuTitle)) {
                if(headerMenu != null) {
                    headerMenus.add(headerMenu);
                }
                headerMenu = new HeaderMenu(currentMenuTitle);
            }
        }
        if(headerMenu != null) {
            headerMenus.add(headerMenu);
        }
        return headerMenus;
    }

    private static class HeaderMenu {
        private String menuTitle;
        private List<String> subMenus = new ArrayList<>();

        public HeaderMenu(String menuTitle) {
            this.menuTitle = menuTitle;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public void setMenuTitle(String menuTitle) {
            this.menuTitle = menuTitle;
        }

        public List<String> getSubMenus() {
            return subMenus;
        }

        public void setSubMenus(List<String> subMenus) {
            this.subMenus = subMenus;
        }
    }
}
