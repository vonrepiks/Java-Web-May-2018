package org.softuni.dtos.sales;

public class CreateFinalSaleDto {

    private Long carId;

    private Long customerId;

    private long discount;

    public CreateFinalSaleDto() {
    }

    public Long getCarId() {
        return this.carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public long getDiscount() {
        return this.discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
