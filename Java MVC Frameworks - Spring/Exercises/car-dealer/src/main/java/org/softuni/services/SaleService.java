package org.softuni.services;

import org.softuni.dtos.sales.CreateFinalSaleDto;
import org.softuni.dtos.sales.LazySaleDto;
import org.softuni.dtos.sales.SaleDto;

import java.util.Set;

public interface SaleService {

    SaleDto findSaleById(long id);

    Set<LazySaleDto> allSales();

    void create(CreateFinalSaleDto createFinalSaleDto);
}
