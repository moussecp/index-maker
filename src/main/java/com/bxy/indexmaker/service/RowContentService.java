package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.RowContent;
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

    @Autowired
    private RowContentRepository rowContentRepository;
    @Autowired
    private ExcelImporter excelImporter;

    public List<RowContent> getFirstCells() {
        return rowContentRepository.findAllRowContents();
    }

    public void loadExcelFileContent() throws IOException, InvalidFormatException {
        excelImporter.importExcelFile();
    }
}
