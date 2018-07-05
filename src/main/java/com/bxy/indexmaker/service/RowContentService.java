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

@Service
@Transactional
public class RowContentService {

    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    //TODO use real hibernate Repository
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    @Autowired
    private ExcelImporter excelImporter;


    public List<RowContent> getAllRowContents() {
        return rowContentRepository.findAllRowContents();
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
            RowContentFactory.Builder builder = RowContentFactory.builder();
            if (cell_0 != null) builder.setFirstCell(cell_0.toString());
            if (cell_1 != null) builder.setSecondCell(cell_1.toString());
            if (cell_2 != null) builder.setThirdCell(cell_2.toString());
            if (cell_3 != null) builder.setFourthCell(cell_3.toString());
            addRowContent(builder.build());
    }

    public void calculateIndex() {
        List<Reference> references = new ArrayList<>();
        for(RowContent rowContent : rowContentRepository.findAllRowContents()) {
            for(String content : rowContent.getAllCellContents()) {
                for(String word : content.split(" ")) {
                    referenceRepository.createOrUpdateReference(word, rowContent);
                }
            }
        }
    }

    public List<Reference> getReferencesSortedByCount() {
        List<Reference> allReferences = referenceRepository.findTopReferencesMinusBlackListed();
        allReferences.sort(Comparator.comparing(Reference::getCount).reversed());
        return allReferences;
    }
}
