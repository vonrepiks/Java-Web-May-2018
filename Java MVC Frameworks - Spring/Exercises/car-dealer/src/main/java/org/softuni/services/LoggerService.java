package org.softuni.services;

import org.softuni.dtos.logs.LogDto;

import java.util.Set;

public interface LoggerService {

    Set<LogDto> findAllLogs();

    void create(LogDto logDto);

    void deleteAll();
}
