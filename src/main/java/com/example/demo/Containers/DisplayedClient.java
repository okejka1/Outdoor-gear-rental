package com.example.demo.Containers;

public class DisplayedClient {
    private int ID;
    private String imie;
    private String nazwisko;
    private String nr_telefonu;

    public DisplayedClient(int ID, String imie, String nazwisko, String nr_telefonu) {
        this.ID = ID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
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
}
