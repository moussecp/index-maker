package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.*;
import com.bxy.indexmaker.service.FilePathService;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.bxy.indexmaker.service.html.HtmlHeaderStructureService.buildHeadersStructure;
import static org.junit.Assert.assertEquals;

public class HtmlGeneratorServiceTest {

    private static final String CHAPTER = "chapter";
    private static final String SUB_CHAPTER = "subChapter";
    private static final String SECTION = "section";
    private static final String SUB_SECTION = "subSection";
    private static final String SUB_SUB_SECTION = "subSubSection";
    private static final String NOTES = "notes";
    private static final String WORD = "word";
    private static final String PARAGRAPH_WITH_ACCENTS = "Le site web de vidéo à la demande Hulu annonce le projet en avril 2016, avec l'actrice Elisabeth Moss dans le rôle principal. Adapté du roman éponyme de Margaret Atwood, La Servante écarlate, publié en 1985, la série est créée par Bruce Miller, qui en est également producteur exécutif, avec Daniel Wilson, Fran Sears, et Warren Littlefield.\n" +
            "\n" +
            "Margaret Atwood est productrice et consultante sur le projet, notamment sur les parties du synopsis qui extrapolent le roman, ou le modernisent. Elle fait également une apparition courte dans le tout premier épisode. En juin 2016, Reed Morano a été désignée comme la réalisatrice de la série.\n" +
            "\n" +
            "La première bande-annonce a été diffusée par Hulu sur YouTube le 23 mars 2017. ";
    public static final String N_A = "N/A";
    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
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
        String result = htmlGeneratorService.getFirstTwentyReferencesFormatted(references);
        assertEquals("<div class=\"row\" align=\"center\" ><font size=\"7.0\">word_49</font> <font size=\"7.0\">word_48</font> <font size=\"6.0\">word_47</font> <font size=\"6.0\">word_46</font> <font size=\"5.0\">word_45</font> <font size=\"5.0\">word_44</font> <font size=\"4.0\">word_43</font> <font size=\"4.0\">word_42</font> <font size=\"3.0\">word_41</font> <font size=\"3.0\">word_40</font> </div>",
                result);
    }

    @Test
    public void getChapterStyle() {
        String chapterStyle = HtmlTagsUtils.getChapterStyle();
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
                .setContent(PARAGRAPH_WITH_ACCENTS)
                .setChapter(CHAPTER + "0")
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + "1")
                .setChapter(CHAPTER+ "1")
                .setSubChapter(SUB_CHAPTER+ "1")
                .setSection(SECTION)
                .setSubSection(SUB_SECTION)
                .setSubSubSection(SUB_SUB_SECTION)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + "2")
                .setChapter(CHAPTER+ "1")
                .setSubChapter(SUB_CHAPTER+ "1")
                .setSection(SECTION)
                .setSubSection(SUB_SECTION)
                .setSubSubSection(SUB_SUB_SECTION)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + "3")
                .setChapter(CHAPTER+ "1")
                .setSubChapter(SUB_CHAPTER+ "1")
                .setSection(SECTION)
                .setSubSection(SUB_SECTION)
                .setSubSubSection(SUB_SUB_SECTION)
                .build());

//        String body = htmlGeneratorService.buildBody(rowContents);
        String body = htmlGeneratorService.buildBodyContentWihStructure(rowContents, buildHeadersStructure(rowContents));
        String title = "TITLE";
        String style = HtmlTagsUtils.getChapterStyle();
        String bodyIndex = htmlGeneratorService.getFirstTwentyReferencesFormatted(references);
        String htmlString = htmlGeneratorService.generateHtmlString(body, title, style, bodyIndex);

        File newHtmlFile = new File(FilePathService.getExportedHtmlTestFilePath() + "test.html");
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.write(newHtmlFile, htmlString, StandardCharsets.UTF_8);
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