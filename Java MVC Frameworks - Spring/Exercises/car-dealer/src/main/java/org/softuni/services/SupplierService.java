package org.softuni.services;

import org.softuni.dtos.suppliers.CreateSupplierDto;
import org.softuni.dtos.suppliers.EditSupplierDto;
import org.softuni.dtos.suppliers.SupplierDto;
import org.softuni.dtos.suppliers.SupplierDtoForCreatingPart;

import java.util.Set;

public interface SupplierService {

    Set<SupplierDto> findAllByImporter(boolean isImporter);

    Set<SupplierDtoForCreatingPart> findAllSuppliers();

    void create(CreateSupplierDto createSupplierDto);

    EditSupplierDto findById(Long id);

    void edit(EditSupplierDto editSupplierDto, Long id);

    void deleteById(Long id);
}
