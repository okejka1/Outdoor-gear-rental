package com.example.demo.Containers;

public class EquipmentEntityRental {
    private Integer rentalID;
    private Integer equipmentEntityID;

    public EquipmentEntityRental(Integer rentalID, Integer equipmentEntityID) {
        this.rentalID = rentalID;
        this.equipmentEntityID = equipmentEntityID;
    }

    public Integer getRentalID() {
        return rentalID;
    }

    public Integer getEquipmentEntityID() {
        return equipmentEntityID;
    }
}
