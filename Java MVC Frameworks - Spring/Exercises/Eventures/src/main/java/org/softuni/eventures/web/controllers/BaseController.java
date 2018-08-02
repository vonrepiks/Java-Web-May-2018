package org.softuni.eventures.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    private static final String LAYOUT_VIEW_NAME = "layout";

    protected BaseController() {
    }

    private String getViewModelName(Object viewModel) {
        return String.valueOf(viewModel.getClass().getSimpleName().charAt(0)).toLowerCase() +
                viewModel.getClass().getSimpleName().substring(1);
    }

    protected ModelAndView view(String viewName, Object viewModel) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName(LAYOUT_VIEW_NAME);
        modelAndView.addObject("viewName", viewName);

        if (viewModel != null) {
            //TODO replace hardcode name with dynamic
            String viewModelName = this.getViewModelName(viewModel);
            modelAndView.addObject("viewModel", viewModel);
        }

        return modelAndView;
    }

    protected ModelAndView view(String viewName) {
        return this.view(viewName, null);
    }

    protected ModelAndView redirect(String redirectUrl) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + redirectUrl);

        return modelAndView;
    }
}
