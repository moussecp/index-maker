package com.bxy.indexmaker.importer;

import com.bxy.indexmaker.configuration.XlsFileAddress;
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

    private RowContentRepository rowContentRepository;
    private XlsFileAddress xlsFileAddress;

    @Autowired
    public ExcelImporter(RowContentRepository rowContentRepository, XlsFileAddress xlsFileAddress) {
        this.rowContentRepository = rowContentRepository;
        this.xlsFileAddress = xlsFileAddress;
    }

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


}
