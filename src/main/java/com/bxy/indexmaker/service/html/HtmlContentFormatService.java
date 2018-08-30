package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.RowContent;

public class HtmlContentFormatService {

    public static String getFormattedContent(RowContent rowContent) {
        return rowContent.getContent();
    }

//    private static String addNewLinesForLists(String content) {
//        return content.replace(" - ", "</br> - ");
//    }
}
