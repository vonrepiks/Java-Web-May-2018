package org.softuni.app.services;

import org.softuni.app.models.dto.CapitalDto;

import java.util.Set;

public interface CapitalService {

    Set<CapitalDto> findAll();
}
