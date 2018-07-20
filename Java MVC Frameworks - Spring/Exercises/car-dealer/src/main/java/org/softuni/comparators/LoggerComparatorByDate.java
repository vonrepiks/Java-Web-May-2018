package org.softuni.comparators;

import org.softuni.dtos.logs.LogDto;

import java.util.Comparator;

public class LoggerComparatorByDate implements Comparator<LogDto> {
    @Override
    public int compare(LogDto log1, LogDto log2) {
        return log1.getModifyingDate().compareTo(log2.getModifyingDate());
    }
}
