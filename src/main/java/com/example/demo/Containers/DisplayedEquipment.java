package com.example.demo.Containers;

public class DisplayedEquipment {
    private String kategoria;
    private String nazwa;
    private String cena_wypozyczenia;
    private String promocja;
    private int ID;

    public DisplayedEquipment(String kategoria, String nazwa, String cena_wypozyczenia, String promocja, int ID) {
        this.kategoria = kategoria;
        this.nazwa = nazwa;
        this.cena_wypozyczenia = cena_wypozyczenia;
        this.promocja = promocja;
        this.ID = ID;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getCena_wypozyczenia() {
        return cena_wypozyczenia;
    }

    public String getPromocja() {
        return promocja;
    }

    public int getID() {
        return ID;
    }
}
