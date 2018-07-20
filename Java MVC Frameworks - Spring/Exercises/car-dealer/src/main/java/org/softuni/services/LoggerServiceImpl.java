package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.comparators.LoggerComparatorByDate;
import org.softuni.dtos.logs.LogDto;
import org.softuni.entities.Log;
import org.softuni.repositories.LoggerRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl implements LoggerService {

    private final LoggerRepository loggerRepository;

    private final ModelMapper modelMapper;

    public LoggerServiceImpl(LoggerRepository loggerRepository, ModelMapper modelMapper) {
        this.loggerRepository = loggerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<LogDto> findAllLogs() {
        List<Log> logs = this.loggerRepository.findAll();

        Set<LogDto> logDtos = new LinkedHashSet<>();
        for (Log log : logs) {
            logDtos.add(this.modelMapper.map(log, LogDto.class));
        }

        Set<LogDto> sortedLogs = logDtos.stream().sorted(new LoggerComparatorByDate()).collect(Collectors.toCollection(LinkedHashSet::new));

        return sortedLogs;
    }

    @Override
    public void create(LogDto logDto) {
        Log log = this.modelMapper.map(logDto, Log.class);
        this.loggerRepository.save(log);
    }

    @Override
    public void deleteAll() {
        this.loggerRepository.deleteAll();
    }
}
