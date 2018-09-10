package com.bxy.indexmaker.app;

import com.bxy.indexmaker.domain.ReferenceMapDao;
import com.bxy.indexmaker.domain.ReferenceRepository;
import com.bxy.indexmaker.domain.RowContentMapDao;
import com.bxy.indexmaker.domain.RowContentRepository;
import com.bxy.indexmaker.service.RowContentService;
import com.bxy.indexmaker.service.html.HtmlGeneratorService;
import com.bxy.indexmaker.service.importer.ExcelImporter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class WebpageGenerator {

    private RowContentRepository rowContentRepository = new RowContentMapDao();
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    private RowContentService rowContentService = new RowContentService(rowContentRepository, referenceRepository);
    private HtmlGeneratorService htmlGeneratorService = new HtmlGeneratorService(rowContentRepository, referenceRepository);
    private ExcelImporter excelImporter = new ExcelImporter(rowContentService);

    public static void main (String[] args) throws IOException, InvalidFormatException {
        WebpageGenerator webpageGenerator = new WebpageGenerator();
        webpageGenerator.generateWebpage();
    }

    private void generateWebpage() throws IOException, InvalidFormatException {
        excelImporter.loadExcelFileContentIfEmpty();
        rowContentService.calculateIndexIfEmpty();
        htmlGeneratorService.generateAllChapters();
    }
}
