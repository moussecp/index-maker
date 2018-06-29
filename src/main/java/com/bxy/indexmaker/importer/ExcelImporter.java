package com.bxy.indexmaker.importer;

import com.bxy.indexmaker.persistence.RowContent;
import com.bxy.indexmaker.persistence.RowContentRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelImporter {

    @Autowired
    private RowContentRepository rowContentRepository;
    private XlsFileAddress xlsFileAddress =XlsFileAddress.builder()
            .setXlsFilePath("/home/tms/workspace/indexmaker/src/main/resources/")
            .setXlsFileName("test.xls")
            .build();


    public ExcelImporter() {}

    public void importExcelFile() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(xlsFileAddress.get()));
        doImportExcelFile(workbook);
    }

    private void doImportExcelFile(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            rowContentRepository.addRowContent(new RowContent(sheet.getRow(i).getCell(0).toString()));
        }
    }

    public void setRowContentRepository(RowContentRepository rowContentRepository) {
        this.rowContentRepository = rowContentRepository;
    }

    public void setXlsFileAddress(XlsFileAddress xlsFileAddress) {
        this.xlsFileAddress = xlsFileAddress;
    }
}
