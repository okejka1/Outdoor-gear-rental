package com.example.demo.Containers;

import java.util.Date;

public class DisplayedRental2 {
    private int ID;
    private Date data_wypozyczenia;
    private Date data_oddania;
    private String imie;
    private String nazwisko;
    private String nr_telefonu;
    private int empId;

    public DisplayedRental2(int ID, Date data_wypozyczenia, Date data_oddania, String imie, String nazwisko, String nr_telefonu, int empId) {
        this.ID = ID;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_oddania = data_oddania;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
        this.empId = empId;
    }

    public int getID() {
        return ID;
    }

    public Date getData_wypozyczenia() {
        return data_wypozyczenia;
    }

    public Date getData_oddania() {
        return data_oddania;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public int getEmpId() {
        return empId;
    }
}
