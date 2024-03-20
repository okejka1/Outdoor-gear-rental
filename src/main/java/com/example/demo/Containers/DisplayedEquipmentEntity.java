package com.example.demo.Containers;

public class DisplayedEquipmentEntity {
    private Integer ID;
    private String nazwa_sprzetu;
    private String kategoria;
    private Double cena;
    private String promocja;
    private String kolor;
    private String rozmiar;
    private String status;

    public DisplayedEquipmentEntity(Integer ID, String nazwa_sprzetu, String kategoria, Double cena, String promocja, String kolor, String rozmiar, String status) {
        this.ID = ID;
        this.nazwa_sprzetu = nazwa_sprzetu;
        this.kategoria = kategoria;
        this.cena = cena;
        this.promocja = promocja;
        this.kolor = kolor;
        this.rozmiar = rozmiar;
        this.status = status;
    }

    public String getKategoria() {
        return kategoria;
    }

    public Double getCena() {
        return cena;
    }

    public String getPromocja() {
        return promocja;
    }

    public String getKolor() {
        return kolor;
    }

    public String getRozmiar() {
        return rozmiar;
    }

    public String getStatus() {
        return status;
    }

    public Integer getID() {
        return ID;
    }

    public String getNazwa_sprzetu() {
        return nazwa_sprzetu;
    }
}
