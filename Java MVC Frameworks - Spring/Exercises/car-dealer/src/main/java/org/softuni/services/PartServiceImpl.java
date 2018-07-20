package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.dtos.parts.CreatePartDto;
import org.softuni.dtos.parts.EditPartDto;
import org.softuni.entities.Part;
import org.softuni.entities.Supplier;
import org.softuni.repositories.CarRepository;
import org.softuni.repositories.PartRepository;
import org.softuni.repositories.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    private final SupplierRepository supplierRepository;

    private final CarRepository carRepository;

    private ModelMapper modelMapper;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, CarRepository carRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void create(CreatePartDto createPartDto) {
        Supplier supplier = this.supplierRepository.findById(Long.valueOf(createPartDto.getSupplier())).get();

        Part part = new Part();
        part.setName(createPartDto.getName());
        part.setPrice(createPartDto.getPrice());
        part.setSupplier(supplier);
        part.setQuantity(1);

        this.partRepository.save(part);
    }

    @Override
    public void edit(EditPartDto editPartDto, Long id) {
        Part part = this.partRepository.findById(id).get();

        part.setQuantity(editPartDto.getQuantity());
        part.setPrice(editPartDto.getPrice());

        this.partRepository.save(part);
    }

    @Override
    public EditPartDto findById(Long id) {
        Part part = this.partRepository.findById(id).get();
        return this.modelMapper.map(part, EditPartDto.class);
    }

    @Override
    public Set<EditPartDto> findAllParts() {
        List<Part> parts = this.partRepository.findAll();

        Set<EditPartDto> editPartDtos = new LinkedHashSet<>();

        for (Part part : parts) {
            editPartDtos.add(this.modelMapper.map(part, EditPartDto.class));
        }

        return editPartDtos;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.partRepository.deleteRelationsInJoinPartsCarsTable(id);
        this.partRepository.deletePartById(id);
    }
}
