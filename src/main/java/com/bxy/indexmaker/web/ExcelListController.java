package com.bxy.indexmaker.web;


import com.bxy.indexmaker.domain.ReferenceMapDao;
import com.bxy.indexmaker.domain.ReferenceRepository;
import com.bxy.indexmaker.domain.RowContentMapDao;
import com.bxy.indexmaker.domain.RowContentRepository;
import com.bxy.indexmaker.service.RowContentService;
import com.bxy.indexmaker.service.html.HtmlGeneratorService;
import com.bxy.indexmaker.service.importer.ExcelImporter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@Transactional
public class ExcelListController {

    public static final String EXCEL_LIST = "excel/list";
    public static final String EXCEL_IMPORT = "excel/add";
    public static final String EXCEL_INDEX = "excel/index";
    public static final String EXCEL_CALCULATE = "excel/calculateIndex";
    public static final String GENERATE_EVERYTHING = "";
    public static final String GENERATE_CHAPTER1 = "excel/generateChapter1";
    public static final String GENERATE_CHAPTER2 = "excel/generateChapter2";
    public static final String GENERATE_CHAPTER3 = "excel/generateChapter3";
    public static final String HTML_GENERATORS = "excel/generators";

    private RowContentRepository rowContentRepository = new RowContentMapDao();
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    private RowContentService rowContentService = new RowContentService(rowContentRepository, referenceRepository);
    private HtmlGeneratorService htmlGeneratorService = new HtmlGeneratorService(rowContentRepository, referenceRepository);
    private ExcelImporter excelImporter = new ExcelImporter(rowContentService);

    //TODO use ExceptionHandler
//    @ExceptionHandler(Throwable.class)
//    public ModelAndView handleError(Throwable e) {
//        return new IndexMakerExceptionHandler(e).getResponse();
//    }

    @RequestMapping("/" + EXCEL_LIST)
    String showList(Model model) {
        model.addAttribute("rowContents", rowContentService.getAllRowContents());
        return EXCEL_LIST;
    }

    @RequestMapping("/" + EXCEL_IMPORT)
    String importList() throws IOException, InvalidFormatException {
        excelImporter.loadExcelFileContent();
        return "redirect:/" + EXCEL_LIST;
    }

    @RequestMapping("/" + EXCEL_INDEX)
    String showIndex(Model model) throws IOException, InvalidFormatException {
        model.addAttribute("indexedReferences", rowContentService.getReferencesSortedByCount());
        return EXCEL_INDEX;
    }

    @RequestMapping("/" + EXCEL_CALCULATE)
    String calculateIndex(Model model) throws IOException, InvalidFormatException {
        rowContentService.calculateIndex();
        model.addAttribute("indexedReferences", rowContentService.getReferencesSortedByCount());
        return "redirect:/" + EXCEL_INDEX;
    }

    @RequestMapping("/" + GENERATE_EVERYTHING)
    String generateEverything(Model model) throws IOException, InvalidFormatException {
        init();
        htmlGeneratorService.generateAllChapters();
//        htmlGeneratorService.generateChapter1();
//        htmlGeneratorService.generateChapter2();
//        htmlGeneratorService.generateChapter3();
//        htmlGeneratorService.generateChapter4();
        return HTML_GENERATORS;
    }

    @RequestMapping("/" + GENERATE_CHAPTER1)
    String generateChapter1Html(Model model) throws IOException, InvalidFormatException {
        init();
        htmlGeneratorService.generateChapter1();
        return HTML_GENERATORS;
    }

    @RequestMapping("/" + GENERATE_CHAPTER2)
    String generateChapter2Html(Model model) throws IOException, InvalidFormatException {
        init();
        htmlGeneratorService.generateChapter2();
        return HTML_GENERATORS;
    }

    @RequestMapping("/" + GENERATE_CHAPTER3)
    String generateChapter3Html(Model model) throws IOException, InvalidFormatException {
        init();
        htmlGeneratorService.generateChapter3();
        return HTML_GENERATORS;
    }

    @RequestMapping("/" + HTML_GENERATORS)
    String showGenerators(Model model) throws IOException, InvalidFormatException {
        init();
        return HTML_GENERATORS;
    }

    private void init() throws IOException, InvalidFormatException {
        excelImporter.loadExcelFileContentIfEmpty();
        rowContentService.calculateIndexIfEmpty();
    }
}