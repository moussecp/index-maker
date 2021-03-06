package com.bxy.indexmaker.service.importer;

import com.bxy.indexmaker.service.FilePathService;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelImporter {

    public static final String XLS_FILE_NAME = "programme-ecolo.xls";
    private RowContentService rowContentService;
    //TODO use properties file
    private XlsFileAddress xlsFileAddress = XlsFileAddress.builder()
            .setXlsFilePath(FilePathService.getImportedXlsFilePath())
            .setXlsFileName(XLS_FILE_NAME)
            .build();


    public ExcelImporter(RowContentService rowContentService) {
        this.rowContentService = rowContentService;
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
                if (row != null && !"Contenu".equals(row.getCell(0))) {
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

    public void loadExcelFileContentIfEmpty() throws IOException, InvalidFormatException {
        if (rowContentService.getRowContentRepository().findAll().isEmpty()) {
            importExcelFile();
            System.out.println("Excel file loaded");
        } else {
            System.out.println("Excel file already loaded");
        }
    }

    public void loadExcelFileContent() throws IOException, InvalidFormatException {
        importExcelFile();
    }
}
