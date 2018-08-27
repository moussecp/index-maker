package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlGeneratorServiceTest {

    private static final String CHAPTER = "chapter";
    private static final String SUB_CHAPTER = "subChapter";
    private static final String SECTION = "section";
    private static final String SUB_SECTION = "subSection";
    private static final String SUB_SUB_SECTION = "subSubSection";
    private static final String NOTES = "notes";
    private static final String WORD = "word";
    public static final String N_A = "N/A";
    private final HtmlGeneratorService htmlGeneratorService = new HtmlGeneratorService();
    private List<Reference> references = new ArrayList<>();
    private ReferenceRepository referenceRepository = new ReferenceMapDao();

    @Before
    public void setup() {
        htmlGeneratorService.setReferenceRepository(referenceRepository);

        for (int i = 0; i < 50; i++) {
            Reference reference = buildReference(i);
            for(int j = 0; j < i ; j++) {
                reference.addRowContent(buildRowContent(j));
            }
            referenceRepository.persist(reference);
            references.add(reference);
        }
    }

    private Reference buildReference(int i) {
        return Reference.builder()
                .setWord(WORD + "_" + i)
                .setSubChapter(SUB_CHAPTER)
                .setRowContent(
                        buildRowContent()
                )
                .build();
    }

    private RowContent buildRowContent(int i) {
        return RowContentFactory.builder()
                .setContent(WORD + "_" + i)
                .setChapter(CHAPTER + "_" + i)
                .setSubChapter(SUB_CHAPTER + "_" + i)
                .setSection(SECTION + "_" + i)
                .setSubSection(SUB_SECTION + "_" + i)
                .setSubSubSection(SUB_SUB_SECTION + "_" + i)
                .setNotes(NOTES + "_" + i)
                .build();
    }

    private RowContent buildRowContent() {
        return buildRowContent(0);
    }

    private List<RowContent> buildRowContents() {
        return buildRowContents(5);
    }

    private List<RowContent> buildRowContents(int index) {
        List<RowContent> rowContents = new ArrayList<>();
        for(int i = 0; i< index; i++) {
            rowContents.add(buildRowContent(i));
        }
        return rowContents;
    }

//    @Test
//    public void getFirstFiveReferences() {
//        String firstFiveReferences = htmlGeneratorService.getFirstFiveReferences(references);
//        assertEquals("word_49 ; word_48 ; word_47 ; word_46 ; word_45", firstFiveReferences);
//    }

//    @Test
//    public void getFirstFiveReferencesWithEmptyReferences() {
//        String firstFiveReferences = htmlGeneratorService.getFirstFiveReferences(new ArrayList<Reference>());
//        assertEquals("", firstFiveReferences);
//    }

//    @Test
//    public void getFirstReferences() {
//        String firstReference = htmlGeneratorService.getTopReferences(references, 1);
//        assertEquals("word_49", firstReference);
//
//        String first3References = htmlGeneratorService.getTopReferences(references, 3);
//        assertEquals("word_49 ; word_48 ; word_47", first3References);
//
//    }

    @Test
    public void getFormatedFirstTenReferences() {
        String result = htmlGeneratorService.getFirstTenReferencesFormated(references);
        assertEquals("<div class=\"row\" align=\"center\" ><font size=\"7.0\">word_49</font> <font size=\"7.0\">word_48</font> <font size=\"6.0\">word_47</font> <font size=\"6.0\">word_46</font> <font size=\"5.0\">word_45</font> <font size=\"5.0\">word_44</font> <font size=\"4.0\">word_43</font> <font size=\"4.0\">word_42</font> <font size=\"3.0\">word_41</font> <font size=\"3.0\">word_40</font> </div>",
                result);
    }

    @Test
    public void getChapterStyle() {
        String chapterStyle = htmlGeneratorService.getChapterStyle();
        String expected = "        h2 {\n" +
                "            background: url('images/header-test.png') no-repeat left top;\n" +
                "            color: white;\n" +
                "            /*width: 200px;*/\n" +
                "            /*height: 50px;*/\n" +
                "        }        .text-block {\n" +
                "            position: absolute;\n" +
                "            top: 50%;\n" +
                "            left: 50%;\n" +
                "            /*transform: translate(-200%, -20%);*/\n" +
                "            background-color: black;\n" +
                "            color: white;\n" +
                "            padding-left: 20px;\n" +
                "            padding-right: 20px;\n" +
                "        }";
        System.out.println(chapterStyle);
        assertEquals(expected,chapterStyle);
    }

    @Test
    public void buildBodyWithMockedChapter() {
        List<RowContent> rowContents = buildRowContents(5);
        HtmlGeneratorService mockedService = Mockito.mock(HtmlGeneratorService.class);
        Mockito.when(mockedService.buildBody(rowContents)).thenCallRealMethod();
        String body = mockedService.buildBody(rowContents);
        assertEquals("",body);
    }

    @Test
    public void buildChapterWithMockedSubChapter() {
        List<RowContent> rowContents = buildRowContents(5);
        HtmlGeneratorService mockedService = Mockito.mock(HtmlGeneratorService.class);
        int indexChapter = 0;
        Mockito.when(mockedService.buildChapter(rowContents, indexChapter)).thenCallRealMethod();
        Mockito.when(mockedService.buildSubChapter(rowContents, indexChapter)).thenReturn("");
        String expected = "<div class=\"blog-header\" ><div class=\"container\"><h1 class=\"blog-title\">chapter_0</h1></div></div>";
        String chapter = mockedService.buildChapter(rowContents, indexChapter);
        assertEquals(expected,chapter);
    }

    @Test
    public void buildSubChapter() {
        //TODO
    }

    @Test
    public void isHeaderContent() {
        //TODO
    }

    @Test
    public void h1ClassBlogTitle() {
        //TODO
    }

    @Test
    public void heading() {
        //TODO
    }

    @Test
    public void chapterOpeningDiv() {
        //TODO
    }

    @Test
    public void chapterClosingDiv() {
        //TODO
    }

    @Test
    public void openingDivClassContainer() {
        //TODO
    }

    @Test
    public void openingDivClassBlogHeader() {
        //TODO
    }

    @Test
    public void openingDivClassContainerFluid() {
        //TODO
    }

    @Test
    public void openingDivClassTextBlock() {
        //TODO
    }

    @Test
    public void closingDiv() {
        //TODO
    }

    @Test
    public void openingParagraphClassLeadBlogDescription() {
        //TODO
    }

    @Test
    public void closingParagraph() {
        //TODO
    }

    @Test
    public void generateHtmlFile() {
    //TODO
    }

    @Test
    public void generateHtmlString() throws IOException {
        List<RowContent> rowContents = new ArrayList<>();
        rowContents.add(RowContentFactory.builder()
                .setContent(WORD)
                .setChapter(CHAPTER)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(WORD)
                .setChapter(CHAPTER)
                .setSubChapter(SUB_CHAPTER)
                .build());

        String body = htmlGeneratorService.buildBody(rowContents);
        String title = "TITLE";
        String style = htmlGeneratorService.getChapterStyle();
        String bodyIndex = htmlGeneratorService.getFirstTenReferencesFormated(references);
        String htmlString = htmlGeneratorService.generateHtmlString(body, title, style, bodyIndex);

        File newHtmlFile = new File(FilePathService.getExportedHtmlTestFilePath() + "test.html");
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    @Test
    public void getRowContentsFromChapter() {
        //TODO
    }

    @Test
    public void getReferencesFromChapterOrSubChapter() {
        //TODO
    }

    @Test
    public void getRowContentsFromSubChapter() {
        //TODO
    }
}