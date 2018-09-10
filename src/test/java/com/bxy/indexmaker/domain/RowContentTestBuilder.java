package com.bxy.indexmaker.domain;

import java.util.Set;
import java.util.TreeSet;

public class RowContentTestBuilder {

    public static final String CHAPTER = "Chapter";
    public static final String SUB_CHAPTER = "Sub Chapter";
    public static final String SECTION = "Section";
    public static final String SUB_SECTION = "Sub Section";
    public static final String SUB_SUB_SECTION = "Sub Sub Section";
    public static final String NOTES = "Notes";
    public static final String WORD = "word";
    public static final String PARAGRAPH_WITH_ACCENTS = "Le site web de vidéo à la demande Hulu annonce le projet en avril 2016, avec l'actrice Elisabeth Moss dans le rôle principal. Adapté du roman éponyme de Margaret Atwood, La Servante écarlate, publié en 1985, la série est créée par Bruce Miller, qui en est également producteur exécutif, avec Daniel Wilson, Fran Sears, et Warren Littlefield.\n" +
            "\n" +
            "Margaret Atwood est productrice et consultante sur le projet, notamment sur les parties du synopsis qui extrapolent le roman, ou le modernisent. Elle fait également une apparition courte dans le tout premier épisode. En juin 2016, Reed Morano a été désignée comme la réalisatrice de la série.\n" +
            "\n" +
            "La première bande-annonce a été diffusée par Hulu sur YouTube le 23 mars 2017. ";
    public static final String N_A = "N/A";
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    public static final String TEXT = "TEXT";
    public static final String EXPECTED_FORMATTED_LIST_TEXT = " - " + TEXT;
    public static int indexChapter = 0;
    public static int indexSubChapter = 0;
    public static int indexSection = 0;
    public static int indexSubSection = 0;
    public static int indexSubSubSection = 0;
    private static RowContentRepository rowContentRepository = new RowContentMapDao();

    public static RowContentRepository getRowContentRepository() {
        return rowContentRepository;
    }

    public static void setRowContentRepository(RowContentRepository newRowContentRepository) {
        rowContentRepository = newRowContentRepository;
    }

    public static void addRichRowContentsToRepository() {
        rowContentRepository.addAll(buildRichRowContents());
        rowContentRepository.addAll(rowContentsStartingWithListElement());
    }

    public static void addListedRowContentsToRepository() {
        rowContentRepository.addAll(rowContentsStartingWithListElement());
    }

    public static Set<RowContent> rowContentsStartingWithListElement() {
        Set<RowContent> rowContents = new TreeSet<>();
        rowContents.add(RowContentFactory.builder().setContent(TEXT).build());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rowContents.add(RowContentFactory.builder()
                        .setContent(getNWhitespace(i) + "-" + getNWhitespace(j) + TEXT)
                        .build());
            }
        }
        return rowContents;
    }

    private static String getNWhitespace(int n) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static Set<RowContent> buildRichRowContents() {
        Set<RowContent> rowContents = new TreeSet<>();
        rowContents.addAll(buildChapterWithLimitedSubContent());
        rowContents.addAll(buildChapterWithList());
        rowContents.addAll(buildChapterAverageSubContentSectionsAndSubSections());
        rowContents.addAll(buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections());
        rowContents.addAll(buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections());
        return rowContents;
    }

    public static Set<RowContent> buildChapterWithMassiveSubContentSectionsSubSectionsAndSubSubSections() {
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
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        // chapter 2 subchapter 1 section 1
        int subChaptNo = indexSubChapter++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo)
                .build());
        //chapter 2 subchapter 1 section 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo++)
                .build());
        //chapter 2 subchapter 1 section 2 subsection 1
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo++)
                .setSection(SECTION + sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo++)
                .setSection(SECTION + sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSubChapter(SUB_CHAPTER + subChaptNo)
                .setSection(SECTION + sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .setSubSubSection(SUB_SUB_SECTION + indexSubSubSection++)
                .build());
        return rowContents;
    }

    public static Set<RowContent> buildChapterAverageSubContentSectionsAndSubSections() {
        Set<RowContent> rowContents = new TreeSet<>();
        //chapter 1 section 1
        int chaptNo = indexChapter++;
        int sectNo = indexSection++;
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM)
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM)
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        //chapter 1 section 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        //chapter 1 section 1 subsection 1
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo++)
                .setSubSection(SUB_SECTION + indexSubSection++)
                .build());
        //chapter 1 section 1 subsection 2
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 1")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(LOREM_IPSUM + " 2")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        return rowContents;
    }

    public static Set<RowContent> buildChapterWithLimitedSubContent() {
        Set<RowContent> rowContents = new TreeSet<>();
        rowContents.add(RowContentFactory.builder()
                .setContent(PARAGRAPH_WITH_ACCENTS)
                .setBold("Margaret Atwood ; Hulu sur YouTube")
                .setChapter(CHAPTER + indexChapter++)
                .build());
        return rowContents;
    }

    public static Set<RowContent> buildChapterWithList() {
        Set<RowContent> rowContents = new TreeSet<>();
        int chaptNo = indexChapter++;
        int sectNo = indexSection++;
        // unordered list
        rowContents.add(RowContentFactory.builder()
                .setContent("Ce qui serait super à voir ici :")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent("une liste")
                .setList("List")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent("avec")
                .setList("list")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent("différents éléments non ordonnés...")
                .setList("lISt")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        //enumerated list
        rowContents.add(RowContentFactory.builder()
                .setContent("Ce qui serait super à voir ici :")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + ++sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent("une liste ")
                .setList("ENUMERATE")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(" avec d'autres")
                .setList("enumerate")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        rowContents.add(RowContentFactory.builder()
                .setContent(" éléments ordonnés...")
                .setList("Enumerate")
                .setChapter(CHAPTER + chaptNo)
                .setSection(SECTION + sectNo)
                .build());
        return rowContents;
    }
}
