package com.bxy.indexmaker.service.importer;

import com.bxy.indexmaker.configuration.PropertyPlaceholderConfiguration;
import com.bxy.indexmaker.service.FilePathService;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = PropertyPlaceholderConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class ExcelImporterTest {

    public static final String FILE_NAME = "test.xls";
    @Mock
    private RowContentService rowContentService;
    private ExcelImporter excelImporter;
    private XlsFileAddress xlsFileAddress =XlsFileAddress.builder()
            .setXlsFilePath(FilePathService.getImportedXlsTestFilePath())
            .setXlsFileName(FILE_NAME)
            .build();

    @Before
    public void setup() {
        excelImporter = new ExcelImporter(rowContentService);
        excelImporter.setRowContentService(rowContentService);
        excelImporter.setXlsFileAddress(xlsFileAddress);
    }

    @Ignore
    @Test
    public void importExcelFile() throws IOException, InvalidFormatException {
        excelImporter.importExcelFile();
        verify(rowContentService, times(3)).addRowContent(any());
    }
}