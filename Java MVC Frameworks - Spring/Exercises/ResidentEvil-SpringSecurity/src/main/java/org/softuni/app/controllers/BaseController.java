package org.softuni.app.controllers;

import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public abstract class BaseController {
    private static final String LAYOUT_VIEW_NAME = "layout";

    protected BaseController() {
    }

    public ModelAndView view(String viewName, Object viewModel, List<ObjectError> errors, String title) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LAYOUT_VIEW_NAME);
        modelAndView.addObject("viewName", viewName);
        String viewModelName = "viewModel";
        if (errors != null) {
            viewModelName = String.valueOf(viewModel.getClass().getSimpleName().charAt(0)).toLowerCase() +
                    viewModel.getClass().getSimpleName().substring(1);
        }
        modelAndView.addObject(viewModelName, viewModel);
        modelAndView.addObject("errors", errors);
        title = title == null ? "Viruses" : title;
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    public ModelAndView view(String viewName) {
        return this.view(viewName, null, null, null);
    }

    public ModelAndView view(String viewName, Object viewModel) {
        return this.view(viewName, viewModel, null, null);
    }

    public ModelAndView view(String viewName, Object viewModel, List<ObjectError> errors) {
        return this.view(viewName, viewModel, errors, null);
    }

    public ModelAndView redirect(String redirectUrl) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + redirectUrl);

        return modelAndView;
    }
}
