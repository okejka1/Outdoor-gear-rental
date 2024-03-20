package com.example.demo.Containers;

public class Employee extends Person{

    private String login;

    private String password;

    private Position position;


    public Employee(int id, String name, String surname, String telephoneNumber, String email, String idNumber, String login, String password,Position position, Adress adress) {
        super(id, name, surname, telephoneNumber, email, idNumber, adress);
        this.login = login;
        this.password = password;
        this.position = position;
    }

    public boolean checkAuth(){
        return false;
    }

    public Position getPosition() {
        return position;
    }
}
