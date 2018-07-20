package org.softuni.app.services;

import org.modelmapper.ModelMapper;
import org.softuni.app.dto.CapitalDto;
import org.softuni.app.entities.Capital;
import org.softuni.app.repositories.CapitalRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CapitalServiceImpl implements CapitalService {

    private CapitalRepository capitalRepository;

    private ModelMapper modelMapper;

    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<CapitalDto> findAll() {
        List<Capital> capitals = this.capitalRepository.findAll();

        Set<CapitalDto> capitalDtos = new HashSet<>();

        for (Capital capital : capitals) {
            capitalDtos.add(this.modelMapper.map(capital, CapitalDto.class));
        }

        return capitalDtos;
    }
}
