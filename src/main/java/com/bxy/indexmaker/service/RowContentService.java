package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RowContentService {

    public static final String LIST_ELEMENT = "[ ]{0,}-[ ]{0,}";
    public static final String LIST_ELEMENT_WITH_WORDS = LIST_ELEMENT + ".*";
    private RowContentRepository rowContentRepository;
    private ReferenceRepository referenceRepository;
    public RowContentService(RowContentRepository rowContentRepository, ReferenceRepository referenceRepository) {
        this.rowContentRepository = rowContentRepository;
        this.referenceRepository = referenceRepository;
    }

    public List<RowContent> getAllRowContents() {
        return rowContentRepository.findAllRowContents();
    }

    public void addRowContent(RowContent rowContent) {
        if (!rowContent.hasAllFieldsEmpty()) {
            String newContent = rowContent.getContent();
            if (rowContent.getId() != 1L && startsWithListElement(newContent)) {
                mergeAndUpdateRowContentsWithListElements(newContent);
            } else {
                rowContentRepository.addRowContent(rowContent);
            }
        }
    }

    public void mergeAndUpdateRowContentsWithListElements(String newContent) {
        RowContent previousRowContent = rowContentRepository.findLastRowContent();
        String previousContent = previousRowContent.getContent();
        previousRowContent.updateContent(mergeContentWithListElement(previousContent, newContent));
        rowContentRepository.updateRowContent(previousRowContent);
    }

    public String mergeContentWithListElement(String previousContent, String newContent) {
        return new StringBuilder(previousContent)
                .append("<br/>")
                .append(newContent.replaceAll(LIST_ELEMENT, " - ")).toString();
    }

    public boolean startsWithListElement(String content) {
        return content.matches(LIST_ELEMENT_WITH_WORDS);
    }

    public void extractRowContent(Row row) {
        Cell content = row.getCell(0);
        Cell chapter = row.getCell(3);
        Cell subChapter = row.getCell(4);
        Cell section = row.getCell(5);
        Cell subSection = row.getCell(6);
        Cell subSubSection = row.getCell(7);
        Cell notes = row.getCell(8);
        Cell list = row.getCell(1);
        Cell bold = row.getCell(2);
        RowContentFactory.Builder builder = RowContentFactory.builder();
        if (content != null) builder.setContent(content.toString());
        if (chapter != null) builder.setChapter(chapter.toString());
        if (subChapter != null) builder.setSubChapter(subChapter.toString());
        if (section != null) builder.setSection(section.toString());
        if (subSection != null) builder.setSubSection(subSection.toString());
        if (subSubSection != null) builder.setSubSubSection(subSubSection.toString());
        if (notes != null) builder.setNotes(notes.toString());
        if (list != null) builder.setList(list.toString());
        if (bold != null) builder.setBold(bold.toString());
        addRowContent(builder.build());
    }

    public void calculateIndexIfEmpty() {
        if (referenceRepository.findAll().isEmpty()) {
            calculateIndex();
            System.out.println("Index calculated");
        } else {
            System.out.println("Index already calculated");
        }
    }

    public List<Reference> calculateIndex() {
        for (RowContent rowContent : rowContentRepository.findAllRowContents()) {
            String subChapter = ((rowContent.getSubChapter() != null)
                    && (!rowContent.getSubChapter().isEmpty())
                    && (!"N/A".equals(rowContent.getSubChapter())))
                    ? rowContent.getSubChapter()
                    : rowContent.getChapter();
            for (String word : rowContent.getContent().split(" ")) {
                String filteredWord = word.trim().replaceAll("[-+.^:,]", "");
                referenceRepository.createOrUpdateReference(
                        filteredWord,
                        subChapter,
                        rowContent);
            }
        }
        return referenceRepository.findAll();
    }

    public List<Reference> getReferencesSortedByCount() {
        List<Reference> allReferences = referenceRepository.findTopReferencesMinusBlackListed();
        allReferences.sort(Comparator.comparing(Reference::getCount).reversed());
        return allReferences;
    }

    public List<String> getAllRowContentChapters(List<RowContent> rowContents) {
        return new ArrayList<>(rowContents
                .stream()
                .sorted()
                .map(rowContent -> rowContent.getChapter())
                .collect(Collectors.toSet()));
    }

    public RowContentRepository getRowContentRepository() {
    return this.rowContentRepository;
    }
}
