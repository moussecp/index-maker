package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import com.bxy.indexmaker.service.importer.ExcelImporter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RowContentService {

    public void setRowContentRepository(RowContentRepository rowContentRepository) {
        this.rowContentRepository = rowContentRepository;
    }

    public void setReferenceRepository(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public void setExcelImporter(ExcelImporter excelImporter) {
        this.excelImporter = excelImporter;
    }

    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    //TODO use real hibernate Repository
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    @Autowired
    private ExcelImporter excelImporter;


    public List<RowContent> getAllRowContents() {
        return rowContentRepository.findAllRowContents();
    }

    public void loadExcelFileContentIfEmpty() throws IOException, InvalidFormatException {
        if(rowContentRepository.findAll().isEmpty()) {
            excelImporter.importExcelFile();
            System.out.println("Excel file loaded");
        } else {
            System.out.println("Excel file already loaded");
        }
    }

    public void loadExcelFileContent() throws IOException, InvalidFormatException {
        excelImporter.importExcelFile();
    }

    public void addRowContent(RowContent rowContent) {
        if (!rowContent.hasAllFieldsEmpty()) {
            rowContentRepository.addRowContent(rowContent);
        }
    }

    public void extractRowContent(Row row) {
        Cell content = row.getCell(0);
        Cell chapter = row.getCell(2);
        Cell subChapter = row.getCell(3);
        Cell section = row.getCell(4);
        Cell subSection = row.getCell(5);
        Cell subSubSection = row.getCell(6);
        Cell notes = row.getCell(7);
        Cell list = row.getCell(1);
        RowContentFactory.Builder builder = RowContentFactory.builder();
        if (content != null) builder.setContent(content.toString());
        if (chapter != null) builder.setChapter(chapter.toString());
        if (subChapter != null) builder.setSubChapter(subChapter.toString());
        if (section != null) builder.setSection(section.toString());
        if (subSection != null) builder.setSubSection(subSection.toString());
        if (subSubSection != null) builder.setSubSubSection(subSubSection.toString());
        if (notes != null) builder.setNotes(notes.toString());
        if (list != null) builder.setList(list.toString());
        addRowContent(builder.build());
    }

    public void calculateIndexIfEmpty() {
        if(referenceRepository.findAll().isEmpty()) {
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
                String filteredWord = word.trim().replaceAll("[-+.^:,]","");
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
}
