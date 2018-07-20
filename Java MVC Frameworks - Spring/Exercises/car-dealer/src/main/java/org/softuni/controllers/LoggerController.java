package org.softuni.controllers;

import org.softuni.dtos.logs.LogDto;
import org.softuni.services.LoggerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/logs")
public class LoggerController extends BaseController {

    private final LoggerService loggerService;

    public LoggerController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @GetMapping("/all")
    public ModelAndView all() {
        Set<LogDto> allLogs = this.loggerService.findAllLogs();
        return super.view("views/logs/all", allLogs);
    }

    @PostMapping("/search")
    public ModelAndView search(@ModelAttribute LogDto logDto) {
        Set<LogDto> logDtos = this.loggerService.findAllLogs();

        if (logDto.getUser() == null || logDto.getUser().isEmpty()) {
            return super.view("views/logs/all", logDtos);
        }

        Set<LogDto> logDtosByUser = logDtos.stream()
                .filter(log -> log.getUser().equals(logDto.getUser()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return super.view("views/logs/all", logDtosByUser);
    }

    @PostMapping("/clear")
    public ModelAndView clear() {
        this.loggerService.deleteAll();
        return super.redirect("/");
    }
}
