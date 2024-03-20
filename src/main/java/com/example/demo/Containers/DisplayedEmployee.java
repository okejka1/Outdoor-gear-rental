package com.example.demo.Containers;

import java.util.Date;

public class DisplayedEmployee {
    String stanowisko;
    Integer ID;
    String imie;
    String nazwisko;
    String nr_telefonu;
    String email;
    String nr_dowodu;
    Date data_zatrudnienia;

    public DisplayedEmployee(String stanowisko, Integer ID, String imie, String nazwisko, String nr_telefonu, String email, String nr_dowodu, Date data_zatrudnienia) {
        this.stanowisko = stanowisko;
        this.ID = ID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
        this.email = email;
        this.nr_dowodu = nr_dowodu;
        this.data_zatrudnienia = data_zatrudnienia;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public Integer getID() {
        return ID;
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

    public String getEmail() {
        return email;
    }

    public String getNr_dowodu() {
        return nr_dowodu;
    }

    public Date getData_zatrudnienia() {
        return data_zatrudnienia;
    }
}
