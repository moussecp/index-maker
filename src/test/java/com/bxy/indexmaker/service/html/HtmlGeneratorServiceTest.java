package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.*;
import com.bxy.indexmaker.service.FilePathService;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.bxy.indexmaker.service.html.HtmlHeaderMenuService.getHeaderMenusAsHtml;
import static com.bxy.indexmaker.service.html.HtmlHeaderStructureService.buildHeadersStructure;
import static org.junit.Assert.assertEquals;

public class HtmlGeneratorServiceTest {

    private static final String CHAPTER = "Chapter";
    private static final String SUB_CHAPTER = "Sub Chapter";
    private static final String SECTION = "Section";
    private static final String SUB_SECTION = "Sub Section";
    private static final String SUB_SUB_SECTION = "Sub Sub Section";
    private static final String NOTES = "Notes";
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
    private RowContentRepository rowContentRepository = new RowContentMapDao();

    @Before
    public void setup() {

    }

    private void generateDummyReferences() {
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
        generateDummyReferences();
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
        addRichRowContentsToRepository();
        RowContentService rowContentService = new RowContentService();
        rowContentService.setRowContentRepository(rowContentRepository);
        rowContentService.setReferenceRepository(referenceRepository);
        List<RowContent> rowContents = rowContentRepository.findAllRowContents();
        List<Reference> references = rowContentService.calculateIndex();
        htmlGeneratorService.setReferenceRepository(referenceRepository);

        String body = htmlGeneratorService.buildBodyContentWihStructure(rowContents, buildHeadersStructure(rowContents));
        String title = "TITLE";
        String style = HtmlTagsUtils.getChapterStyle();
        String bodyIndex = htmlGeneratorService.getFirstTwentyReferencesFormatted(references);
        String bodyHeaderLinks = getHeaderMenusAsHtml(rowContents);
        String htmlString = htmlGeneratorService.generateHtmlString(body, title, style, bodyIndex, bodyHeaderLinks);

        File newHtmlFile = new File(FilePathService.getExportedHtmlTestFilePath() + "test.html");
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.write(newHtmlFile, htmlString, StandardCharsets.UTF_8);
    }

    private void addRichRowContentsToRepository() {
        rowContentRepository.addAll(buildRichRowContents());
    }

    private static int indexChapter = 0;
    private static int indexSubChapter = 0;
    private static int indexSection = 0;
    private static int indexSubSection = 0;
    private static int indexSubSubSection = 0;

    private Set<RowContent> buildRichRowContents() {
        Set<RowContent> rowContents = new TreeSet<>();
        rowContents.addAll(buildChapterWithLimitedSubContent());
        rowContents.addAll(buildChapterAverageSubContentSectionsAndSubSections());
        rowContents.addAll(buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections());
        rowContents.addAll(buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections());
        return rowContents;
    }

    private Set<RowContent> buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections() {
        Set<RowContent> rowContents = new TreeSet<>();
        //chapter 2
        int chaptNo = indexChapter++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .build());
        // chapter 2 section 1
        int sectNo = indexSection++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        // chapter 2 subchapter 1 section 1
        int subChaptNo = indexSubChapter++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        //chapter 2 subchapter 1 section 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo++)
                .build());
        //chapter 2 subchapter 1 section 2 subsection 1
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo++)
                .setSection(SECTION+ sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo++)
                .setSection(SECTION+ sectNo++)
                .setSubSection(SUB_SECTION+ indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo)
                .setSubSection(SUB_SECTION+ indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION+ sectNo++)
                .setSubSection(SUB_SECTION+ indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        return rowContents;
    }

    private Set<RowContent> buildChapterAverageSubContentSectionsAndSubSections() {
        Set<RowContent> rowContents = new TreeSet<>();
        //chapter 1 section 1
        int chaptNo = indexChapter++;
        int sectNo = indexSection++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM)
                .setChapter(CHAPTER+ chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM )
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        //chapter 1 section 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        //chapter 1 section 1 subsection 1
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        //chapter 1 section 1 subsection 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION+ sectNo)
                .build());
        return rowContents;
    }

    private Set<RowContent> buildChapterWithLimitedSubContent() {
        Set<RowContent> rowContents = new TreeSet<>();
        rowContents.add(RowContentFactory.builder()
                .setContent(PARAGRAPH_WITH_ACCENTS)
                .setChapter(CHAPTER + indexChapter++)
                .build());
        return rowContents;
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