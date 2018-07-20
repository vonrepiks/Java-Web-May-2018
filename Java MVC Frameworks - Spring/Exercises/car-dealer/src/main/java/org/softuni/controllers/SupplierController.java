package org.softuni.controllers;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.suppliers.CreateSupplierDto;
import org.softuni.dtos.suppliers.EditSupplierDto;
import org.softuni.dtos.suppliers.SupplierDto;
import org.softuni.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/suppliers")
@PreAuthenticate(loggedIn = true)
public class SupplierController extends BaseController {

    private SupplierService supplierService;


    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/local")
    public ModelAndView getLocalSuppliers() {
        Set<SupplierDto> localSuppliers = this.supplierService.findAllByImporter(false);

        return super.view("views/suppliers/by-importer", localSuppliers);
    }

    @GetMapping("/importers")
    public ModelAndView getImportersSuppliers() {
        Set<SupplierDto> importersSuppliers = this.supplierService.findAllByImporter(true);

        return super.view("views/suppliers/by-importer", importersSuppliers);
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return super.view("views/suppliers/add");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@ModelAttribute CreateSupplierDto createSupplierDto) {
        this.supplierService.create(createSupplierDto);
        return super.redirect("/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        EditSupplierDto editSupplierDto = this.supplierService.findById(id);
        return super.view("views/suppliers/edit", editSupplierDto);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@ModelAttribute EditSupplierDto editSupplierDto, @PathVariable("id") Long id) {
        this.supplierService.edit(editSupplierDto, id);
        return super.redirect("/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        EditSupplierDto editSupplierDto = this.supplierService.findById(id);
        return super.view("views/suppliers/delete", editSupplierDto);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteConfirm(@PathVariable("id") Long id) {
        this.supplierService.deleteById(id);
        return super.redirect("/");
    }
}
