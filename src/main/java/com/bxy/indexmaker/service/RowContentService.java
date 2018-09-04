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
        Cell cell_0 = row.getCell(0);
        Cell cell_1 = row.getCell(1);
        Cell cell_2 = row.getCell(2);
        Cell cell_3 = row.getCell(3);
        Cell cell_4 = row.getCell(4);
        Cell cell_5 = row.getCell(5);
        Cell cell_6 = row.getCell(6);
        RowContentFactory.Builder builder = RowContentFactory.builder();
        if (cell_0 != null) builder.setContent(cell_0.toString());
        if (cell_1 != null) builder.setChapter(cell_1.toString());
        if (cell_2 != null) builder.setSubChapter(cell_2.toString());
        if (cell_3 != null) builder.setSection(cell_3.toString());
        if (cell_4 != null) builder.setSubSection(cell_4.toString());
        if (cell_5 != null) builder.setSubSubSection(cell_5.toString());
        if (cell_6 != null) builder.setNotes(cell_6.toString());
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
