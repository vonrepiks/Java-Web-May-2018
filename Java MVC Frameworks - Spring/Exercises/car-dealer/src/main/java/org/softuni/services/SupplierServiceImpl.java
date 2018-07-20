package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.dtos.suppliers.CreateSupplierDto;
import org.softuni.dtos.suppliers.EditSupplierDto;
import org.softuni.dtos.suppliers.SupplierDto;
import org.softuni.dtos.suppliers.SupplierDtoForCreatingPart;
import org.softuni.entities.Supplier;
import org.softuni.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository;

    private ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<SupplierDto> findAllByImporter(boolean isImporter) {
        List<Supplier> suppliers = this.supplierRepository.findAllSuppliersByImporter(isImporter);

        Set<SupplierDto> supplierDtos = new LinkedHashSet<>();

        for (Supplier supplier : suppliers) {
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setId(supplier.getId());
            supplierDto.setName(supplier.getName());
            supplierDto.setCountOfParts(supplier.getParts().size());

            supplierDtos.add(supplierDto);
        }

        return supplierDtos;
    }

    @Override
    public Set<SupplierDtoForCreatingPart> findAllSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        Set<SupplierDtoForCreatingPart> supplierDtoForCreatingParts = new LinkedHashSet<>();

        for (Supplier supplier : suppliers) {
            supplierDtoForCreatingParts.add(this.modelMapper.map(supplier, SupplierDtoForCreatingPart.class));
        }
        return supplierDtoForCreatingParts;
    }

    @Override
    public void create(CreateSupplierDto createSupplierDto) {
        Supplier supplier = this.modelMapper.map(createSupplierDto, Supplier.class);

        this.supplierRepository.save(supplier);
    }

    @Override
    public EditSupplierDto findById(Long id) {
        Supplier supplier = this.supplierRepository.findById(id).get();

        return this.modelMapper.map(supplier, EditSupplierDto.class);
    }

    @Override
    public void edit(EditSupplierDto editSupplierDto, Long id) {
        Supplier supplier = this.supplierRepository.findById(id).get();
        this.modelMapper.map(editSupplierDto, supplier);

        this.supplierRepository.save(supplier);
    }

    @Override
    public void deleteById(Long id) {
        this.supplierRepository.deleteById(id);
    }
}
