package org.softuni.app.services;

import org.modelmapper.ModelMapper;
import org.softuni.app.dto.VirusCreateDto;
import org.softuni.app.dto.VirusShowDto;
import org.softuni.app.entities.Capital;
import org.softuni.app.entities.Virus;
import org.softuni.app.entities.enums.MagnitudeType;
import org.softuni.app.entities.enums.MutationType;
import org.softuni.app.repositories.CapitalRepository;
import org.softuni.app.repositories.VirusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VirusServiceImpl implements VirusService {

    private VirusRepository virusRepository;

    private CapitalRepository capitalRepository;

    private ModelMapper modelMapper;

    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    private Virus mapCreateVirusDtoToVirusEntity(VirusCreateDto virusCreateDto, String id) {
        Virus virus = new Virus();
        virus.setId(id);
        virus.setName(virusCreateDto.getName());
        virus.setDescription(virusCreateDto.getDescription());
        virus.setSideEffects(virusCreateDto.getSideEffects());
        virus.setCreator(virusCreateDto.getCreator());
        virus.setDeadly(virusCreateDto.isDeadly());
        virus.setCurable(virusCreateDto.isCurable());
        virus.setMutation(MutationType.valueOf(virusCreateDto.getMutation().toUpperCase()));
        virus.setTurnoverRate(virusCreateDto.getTurnoverRate());
        virus.setHoursUntilTurn(virusCreateDto.getHoursUntilTurn());
        virus.setMagnitude(MagnitudeType.valueOf(virusCreateDto.getMagnitude().toUpperCase()));
        virus.setReleasedOn(LocalDate.parse(virusCreateDto.getReleasedOn()));

        Set<Capital> capitals = new HashSet<>();

        for (String capitalName : virusCreateDto.getCapitals()) {
            List<Capital> capitalsByName = this.capitalRepository.findByName(capitalName);
            capitals.addAll(capitalsByName);
        }

        virus.setCapitals(capitals);

        return virus;
    }

    @Override
    public boolean create(VirusCreateDto virusCreateDto) {
        Virus virus = this.mapCreateVirusDtoToVirusEntity(virusCreateDto, null);

        return this.virusRepository.save(virus) != null;
    }

    @Override
    public Set<VirusShowDto> findAll() {
        List<Virus> viruses = this.virusRepository.findAll();
        Set<VirusShowDto> virusShowDtos = new LinkedHashSet<>();

        for (Virus virus : viruses) {
            virusShowDtos.add(this.modelMapper.map(virus, VirusShowDto.class));
        }

        return virusShowDtos;
    }

    @Override
    public VirusCreateDto findById(String id) {
        Optional<Virus> virusOptional = this.virusRepository.findById(id);

        Virus virus = null;
        if (virusOptional.isPresent()) {
            virus = virusOptional.get();
        }

        VirusCreateDto virusCreateDto = null;

        if (virus != null) {
            virusCreateDto = this.modelMapper.map(virus, VirusCreateDto.class);
        }

        return virusCreateDto;
    }

    @Override
    public boolean edit(VirusCreateDto virusCreateDto, String id) {
        Virus virus = this.mapCreateVirusDtoToVirusEntity(virusCreateDto, id);

        return this.virusRepository.saveAndFlush(virus) != null;
    }

    @Override
    public void delete(String id) {
        this.virusRepository.deleteById(id);
    }
}
