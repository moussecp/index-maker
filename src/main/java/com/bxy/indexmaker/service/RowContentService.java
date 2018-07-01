package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.RowContent;
import com.bxy.indexmaker.domain.RowContentFactory;
import com.bxy.indexmaker.domain.RowContentMapDao;
import com.bxy.indexmaker.domain.RowContentRepository;
import com.bxy.indexmaker.service.importer.ExcelImporter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class RowContentService {

    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    @Autowired
    private ExcelImporter excelImporter;
    @Autowired
    private RowContentFactory rowContentFactory;


    public List<RowContent> getFirstCells() {
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

    public void calculateIndex() {

    }
}
