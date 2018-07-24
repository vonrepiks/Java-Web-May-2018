package org.softuni.app.services;

import org.softuni.app.models.dto.viruses.VirusCreateDto;
import org.softuni.app.models.dto.viruses.VirusShowDto;

import java.util.Set;

public interface VirusService {

    boolean create(VirusCreateDto virusCreateDto);

    Set<VirusShowDto> findAll();

    VirusCreateDto findById(String id);

    boolean edit(VirusCreateDto virusCreateDto, String id);

    void delete(String id);
}
