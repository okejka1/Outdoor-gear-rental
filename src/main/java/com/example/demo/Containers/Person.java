package com.example.demo.Containers;

public class Person {
    private final int id;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String email;
    private String idNumber;

    private Adress adress;

    public Person(int id, String name, String surname, String telephoneNumber, String email, String idNumber, Adress adress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Adress getAdress() {
        return adress;
    }
}
