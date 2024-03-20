package com.example.demo.Containers;

import java.util.Date;

public class Rental {
    final int id;
    Date dateOfReturn;
    double wholePrice;
    Date dateOfRental;
    int idClient;
    int idEmployee;

    public Rental(int id, Date dateOfReturn, double wholePrice, Date dateOfRental, int idClient, int  idEmployee) {
        this.id = id;
        this.dateOfReturn = dateOfReturn;
        this.wholePrice = wholePrice;
        this.dateOfRental = dateOfRental;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
    }

}
