package com.bxy.indexmaker.service;

import com.bxy.indexmaker.importer.ExcelImporter;
import com.bxy.indexmaker.persistence.RowContent;
import com.bxy.indexmaker.persistence.RowContentRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RowContentService {

    @Autowired
    private RowContentRepository rowContentRepository;
    @Autowired
    private ExcelImporter excelImporter;

    public List<String> getFirstCells() {
        try {
            excelImporter.importExcelFile();
            return rowContentRepository.findAllRowContents()
                    .stream()
                    .map(rowContent -> rowContent.getFirstCell())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
