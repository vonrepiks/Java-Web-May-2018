package org.softuni.app.models.dto;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

public class VirusAndCapitalsDto {

    @Valid
    private VirusCreateDto virusCreateDto;

    private Set<CapitalDto> capitalDtos;

    public VirusAndCapitalsDto() {
        this.virusCreateDto = new VirusCreateDto();
        this.capitalDtos = new HashSet<>();
    }

    public VirusCreateDto getVirusCreateDto() {
        return this.virusCreateDto;
    }

    public void setVirusCreateDto(VirusCreateDto virusCreateDto) {
        this.virusCreateDto = virusCreateDto;
    }

    public Set<CapitalDto> getCapitalDtos() {
        return this.capitalDtos;
    }

    public void setCapitalDtos(Set<CapitalDto> capitalDtos) {
        this.capitalDtos = capitalDtos;
    }
}
