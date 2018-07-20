package org.softuni.dtos.sales;

import java.math.BigInteger;

public class SalesByCustomerDto {

    private String name;

    private BigInteger countOfSales;

    private Double totalSpentMoney;

    public SalesByCustomerDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCountOfSales() {
        return this.countOfSales;
    }

    public void setCountOfSales(BigInteger countOfSales) {
        this.countOfSales = countOfSales;
    }

    public Double getTotalSpentMoney() {
        return this.totalSpentMoney;
    }

    public void setTotalSpentMoney(Double totalSpentMoney) {
        this.totalSpentMoney = totalSpentMoney;
    }
}
