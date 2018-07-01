package com.bxy.indexmaker.web;


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
    public static final String EXCEL_CALCULATE = "excel/calculateIndex";
    @Autowired
    private RowContentService rowContentService;

    //TODO use ExceptionHandler
//    @ExceptionHandler(Throwable.class)
//    public ModelAndView handleError(Throwable e) {
//        return new IndexMakerExceptionHandler(e).getResponse();
//    }

    @RequestMapping("/" + EXCEL_LIST)
    String showList(Model model) {
        model.addAttribute("firstCells", rowContentService.getFirstCells());
        return EXCEL_LIST;
    }

    @RequestMapping("/" + EXCEL_IMPORT)
    String importList() throws IOException, InvalidFormatException {
        rowContentService.loadExcelFileContent();
        return "redirect:/excel/list";
    }

    @RequestMapping("/" + EXCEL_CALCULATE)
    String calculateIndex(Model model) throws IOException, InvalidFormatException {
        rowContentService.calculateIndex();
        return "/excel/calculateIndex";
    }
}