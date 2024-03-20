package com.example.demo.Containers;

public class DisplayedClient2 {
    private int ID;
    private String imie;
    private String nazwisko;
    private String nr_telefonu;
    private String email;
    private String nr_dowodu;
    private String miasto;
    private String ulica;
    private String numer_domu;
    private String numer_mieszkania;
    private String kod_pocztowy;

    public DisplayedClient2(int ID, String imie, String nazwisko, String nr_telefonu, String email, String nr_dowodu, String miasto, String ulica, String numer_domu, String numer_mieszkania, String kod_pocztowy) {
        this.ID = ID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
        this.email = email;
        this.nr_dowodu = nr_dowodu;
        this.miasto = miasto;
        this.ulica = ulica;
        this.numer_domu = numer_domu;
        this.numer_mieszkania = numer_mieszkania;
        this.kod_pocztowy = kod_pocztowy;
    }

    public int getID() {
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

    public String getMiasto() {
        return miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public String getNumer_domu() {
        return numer_domu;
    }

    public String getNumer_mieszkania() {
        return numer_mieszkania;
    }

    public String getKod_pocztowy() {
        return kod_pocztowy;
    }
}
