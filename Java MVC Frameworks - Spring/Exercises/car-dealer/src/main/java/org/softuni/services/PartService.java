package org.softuni.services;

import org.softuni.dtos.parts.CreatePartDto;
import org.softuni.dtos.parts.EditPartDto;

import java.util.Set;

public interface PartService {

    void create(CreatePartDto createPartDto);

    void edit(EditPartDto editPartDto, Long id);

    EditPartDto findById(Long id);

    Set<EditPartDto> findAllParts();

    void deleteById(Long id);
}
