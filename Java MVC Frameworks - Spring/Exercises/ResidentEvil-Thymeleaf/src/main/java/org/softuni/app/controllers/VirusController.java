package org.softuni.app.controllers;

import org.softuni.app.dto.VirusAndCapitalsDto;
import org.softuni.app.dto.VirusCreateDto;
import org.softuni.app.dto.VirusShowDto;
import org.softuni.app.services.CapitalService;
import org.softuni.app.services.VirusService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private VirusService virusService;

    private CapitalService capitalService;

    public VirusController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute VirusAndCapitalsDto virusAndCapitalsDto) {
        virusAndCapitalsDto.setCapitalDtos(this.capitalService.findAll());
        return super.view("views/viruses/add", virusAndCapitalsDto);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute VirusAndCapitalsDto virusAndCapitalsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            virusAndCapitalsDto.setCapitalDtos(this.capitalService.findAll());
            return super.view("views/viruses/add", virusAndCapitalsDto);
        }
        this.virusService.create(virusAndCapitalsDto.getVirusCreateDto());
        return super.redirect("/viruses");
    }

    @GetMapping("")
    public ModelAndView all() {
        Set<VirusShowDto> virusShowDtos = this.virusService.findAll();
        return super.view("views/viruses/all", virusShowDtos);
    }

    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam("id") String id, @ModelAttribute VirusAndCapitalsDto virusAndCapitalsDto) {
        VirusCreateDto virusCreateDto = this.virusService.findById(id);
        virusAndCapitalsDto.setVirusCreateDto(virusCreateDto);
        virusAndCapitalsDto.setCapitalDtos(this.capitalService.findAll());
        return super.view("views/viruses/edit", virusAndCapitalsDto);
    }

    @PostMapping("/edit")
    public ModelAndView editConfirm(@RequestParam("id") String id, @Valid @ModelAttribute VirusAndCapitalsDto virusAndCapitalsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            virusAndCapitalsDto.setCapitalDtos(this.capitalService.findAll());
            return super.view("views/viruses/edit", virusAndCapitalsDto);
        }
        this.virusService.edit(virusAndCapitalsDto.getVirusCreateDto(), id);
        return super.redirect("/viruses");
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("id") String id) {
        VirusAndCapitalsDto virusAndCapitalsDto = new VirusAndCapitalsDto();
        VirusCreateDto virusCreateDto = this.virusService.findById(id);
        virusAndCapitalsDto.setVirusCreateDto(virusCreateDto);
        virusAndCapitalsDto.setCapitalDtos(this.capitalService.findAll());
        return super.view("views/viruses/delete", virusAndCapitalsDto);
    }

    @PostMapping("/delete")
    public ModelAndView deleteConfirm(@RequestParam("id") String id) {
        this.virusService.delete(id);
        return super.redirect("/viruses");
    }
}
