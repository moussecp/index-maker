package com.bxy.indexmaker.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


class IndexMakerExceptionHandler {
    public static final String EXCEPTION_ATTRIBUTE = "exception";
    public static final String ERROR = "error";
    public static final String ERRORS = "/errors";
    private ModelAndView response;
    private ModelMap model;

    public IndexMakerExceptionHandler(Throwable exception) {
        response = new ModelAndView();
        model = response.getModelMap();
        model.addAttribute(ERROR, exception.getStackTrace());
        model.put(EXCEPTION_ATTRIBUTE, exception);
        setViewName(ERRORS);
    }

    public ModelMap getModel() {
        return model;
    }

    public void setViewName(String viewName) {
        response.setViewName(viewName);
    }

    public ModelAndView getResponse() {
        return response;
    }
}
