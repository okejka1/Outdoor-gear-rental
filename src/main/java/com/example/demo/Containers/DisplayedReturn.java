package com.example.demo.Containers;

import java.util.Date;

public class DisplayedReturn {
    Integer ID;
    Integer wypID;
    Date data_zwrotu;
    Integer empID;
    String imie;
    String nazwisko;

    public DisplayedReturn(Integer ID, Integer wypID, Date data_zwrotu, Integer empID, String imie, String nazwisko) {
        this.ID = ID;
        this.wypID = wypID;
        this.data_zwrotu = data_zwrotu;
        this.empID = empID;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getWypID() {
        return wypID;
    }

    public Date getData_zwrotu() {
        return data_zwrotu;
    }

    public Integer getEmpID() {
        return empID;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
}
