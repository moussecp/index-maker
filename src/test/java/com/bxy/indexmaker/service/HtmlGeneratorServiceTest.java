package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.Reference;
import com.bxy.indexmaker.domain.RowContentFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HtmlGeneratorServiceTest {

    private static final String CHAPTER = "chapter";
    private static final String SUB_CHAPTER = "subChapter";
    private static final String SECTION = "section";
    private static final String SUB_SECTION = "subSection";
    private static final String SUB_SUB_SECTION = "subSubSection";
    private static final String NOTES = "notes";
    private static final String WORD = "word";
    private final HtmlGeneratorService htmlGeneratorService = new HtmlGeneratorService();
    private List<Reference> references = new ArrayList<>();

    @Before
    public void setup() {
        for (int i = 0; i < 50; i++) {
            references.add(Reference.builder()
                    .setWord(WORD + "_" + i)
                    .setSubChapter(SUB_CHAPTER)
                    .setRowContent(
                            RowContentFactory.builder()
                                    .setContent(WORD)
                                    .setChapter(CHAPTER)
                                    .setSubChapter(SUB_CHAPTER)
                                    .setSection(SECTION)
                                    .setSubSection(SUB_SECTION)
                                    .setSubSubSection(SUB_SUB_SECTION)
                                    .setNotes(NOTES)
                                    .build()
                    )
                    .build());
        }
    }

    @Test
    public void getFirstFiveReferences() {
        String firstFiveReferences = htmlGeneratorService.getFirstFiveReferences(references);
        Assert.assertEquals("word_1;word_2", firstFiveReferences);
    }

    @Test
    public void getFirstReferences() {
        String firstReference = htmlGeneratorService.getFirstReferences(references, 1);
        String first3References = htmlGeneratorService.getFirstReferences(references, 3);
    }

    @Test
    public void getChapterStyle() {
    }

    @Test
    public void buildBody() {
    }

    @Test
    public void buildChapter() {
    }

    @Test
    public void buildSubChapter() {
    }

    @Test
    public void isHeaderContent() {
    }

    @Test
    public void h1ClassBlogTitle() {
    }

    @Test
    public void heading() {
    }

    @Test
    public void chapterOpeningDiv() {
    }

    @Test
    public void chapterClosingDiv() {
    }

    @Test
    public void openingDivClassContainer() {
    }

    @Test
    public void openingDivClassBlogHeader() {
    }

    @Test
    public void openingDivClassContainerFluid() {
    }

    @Test
    public void openingDivClassTextBlock() {
    }

    @Test
    public void closingDiv() {
    }

    @Test
    public void openingParagraphClassLeadBlogDescription() {
    }

    @Test
    public void closingParagraph() {
    }

    @Test
    public void generateHtmlFile() {
    }

    @Test
    public void getRowContentsFromChapter() {
    }

    @Test
    public void getReferencesFromChapterOrSubChapter() {
    }

    @Test
    public void getRowContentsFromSubChapter() {
    }
}