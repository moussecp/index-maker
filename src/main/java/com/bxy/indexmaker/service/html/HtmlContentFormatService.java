package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.RowContent;

import java.util.List;

public class HtmlContentFormatService {

    public static String getFormattedContent(RowContent rowContent) {
        String formattedContent = rowContent.getContent().trim();
        List<String> boldText = rowContent.getBoldText();
        for(String text : boldText) {
            if(!text.isEmpty()) {
                formattedContent = formattedContent.replace(text, "<b>" + text + "</b>");
            }
        }
        return formattedContent;
    }
}
