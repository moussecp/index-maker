package com.bxy.indexmaker.service.importer;

import com.bxy.indexmaker.domain.RowContentFactory;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelImporter {

    @Autowired
    private RowContentService rowContentService;
    //TODO use properties file
    private XlsFileAddress xlsFileAddress = XlsFileAddress.builder()
            .setXlsFilePath("/home/tms/workspace/indexmaker/src/main/resources/")
//            .setXlsFilePath("D:\\\\Workspace\\\\index-maker\\\\src\\\\main\\\\resources\\\\")
            .setXlsFileName("programme-ecolo.xls")
            .build();


    public ExcelImporter() {
    }

    public void importExcelFile() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(xlsFileAddress.get()));
        doImportExcelFile(workbook);
    }

    private void doImportExcelFile(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet != null) {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    rowContentService.extractRowContent(row);
                }
            }
        }
    }

    public void setRowContentService(RowContentService rowContentService) {
        this.rowContentService = rowContentService;
    }

    public void setXlsFileAddress(XlsFileAddress xlsFileAddress) {
        this.xlsFileAddress = xlsFileAddress;
    }
}
