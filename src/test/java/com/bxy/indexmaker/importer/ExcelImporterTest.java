package com.bxy.indexmaker.importer;

import com.bxy.indexmaker.configuration.XlsFileAddress;
import com.bxy.indexmaker.persistence.RowContentRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class ExcelImporterTest {

    @Mock
    RowContentRepository rowContentRepository;
    @Mock
    XlsFileAddress xlsFileAddress;
    ExcelImporter excelImporter;

    @Before
    public void setup() {
        when(xlsFileAddress.get()).thenCallRealMethod();
        excelImporter = new ExcelImporter(rowContentRepository, xlsFileAddress);
    }

    @Test
    public void importExcelFile() throws IOException, InvalidFormatException {
        excelImporter.importExcelFile();
    }
}