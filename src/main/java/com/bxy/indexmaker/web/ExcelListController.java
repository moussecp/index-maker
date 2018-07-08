package com.bxy.indexmaker.web;


import com.bxy.indexmaker.service.HtmlGeneratorService;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String GENERATE_INTRO = "excel/generateIntro";
    @Autowired
    private RowContentService rowContentService;
    @Autowired
    private HtmlGeneratorService htmlGeneratorService;

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
        rowContentService.loadExcelFileContent();
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

    @RequestMapping("/" + GENERATE_INTRO)
    String generateIntroHtml(Model model) throws IOException, InvalidFormatException {
        htmlGeneratorService.generateIntro();
        return EXCEL_INDEX;
    }
}