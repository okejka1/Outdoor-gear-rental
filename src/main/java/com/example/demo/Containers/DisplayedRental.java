package com.example.demo.Containers;

import java.util.Date;

public class DisplayedRental {

    Integer ID;
    Integer PRACOWNIKID;
    String imie;
    String nazwisko;
    String kategoria;
    String nazwa;
    Double cena_wypozyczenia;
    Integer egzID;
    String kolor;

    String rozmiar;
    Date data_wypozyczenia;
    Date data_oddania;

    public DisplayedRental(Integer ID, Integer PRACOWNIKID, String imie, String nazwisko, String kategoria, String nazwa, Double cena_wypozyczenia, Integer egzID, String kolor, String rozmiar, Date data_wypozyczenia, Date data_oddania) {
        this.ID = ID;
        this.PRACOWNIKID = PRACOWNIKID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.kategoria = kategoria;
        this.nazwa = nazwa;
        this.cena_wypozyczenia = cena_wypozyczenia;
        this.egzID = egzID;
        this.kolor = kolor;
        this.rozmiar = rozmiar;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_oddania = data_oddania;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getPRACOWNIKID() {
        return PRACOWNIKID;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Double getCena_wypozyczenia() {
        return cena_wypozyczenia;
    }

    public Integer getEgzID() {
        return egzID;
    }

    public String getKolor() {
        return kolor;
    }

    public String getRozmiar() {
        return rozmiar;
    }

    public Date getData_wypozyczenia() {
        return data_wypozyczenia;
    }

    public Date getData_oddania() {
        return data_oddania;
    }
}