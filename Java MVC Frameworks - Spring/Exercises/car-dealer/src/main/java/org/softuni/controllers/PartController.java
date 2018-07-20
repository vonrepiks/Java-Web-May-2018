package org.softuni.controllers;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.parts.CreatePartDto;
import org.softuni.dtos.parts.EditPartDto;
import org.softuni.dtos.suppliers.SupplierDtoForCreatingPart;
import org.softuni.services.PartService;
import org.softuni.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/parts")
@PreAuthenticate(loggedIn = true)
public class PartController extends BaseController {

    private final SupplierService supplierService;

    private final PartService partService;

    public PartController(SupplierService supplierService, PartService partService) {
        this.supplierService = supplierService;
        this.partService = partService;
    }

    @GetMapping("/all")
    public ModelAndView all() {
        Set<EditPartDto> allParts = this.partService.findAllParts();
        return super.view("views/parts/all", allParts);
    }

    @GetMapping("/add")
    public ModelAndView add() {
        Set<SupplierDtoForCreatingPart> allSuppliers = this.supplierService.findAllSuppliers();
        return super.view("views/parts/add", allSuppliers);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@ModelAttribute CreatePartDto createPartDto) {
        this.partService.create(createPartDto);
        return super.redirect("/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        EditPartDto editPartDto = this.partService.findById(id);
        return super.view("views/parts/edit", editPartDto);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@ModelAttribute EditPartDto editPartDto, @PathVariable("id") Long id) {
        this.partService.edit(editPartDto, id);
        return super.redirect("/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        EditPartDto editPartDto = this.partService.findById(id);
        return super.view("views/parts/delete", editPartDto);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteConfirm(@PathVariable("id") Long id) {
        this.partService.deleteById(id);
        return super.redirect("/");
    }
}
