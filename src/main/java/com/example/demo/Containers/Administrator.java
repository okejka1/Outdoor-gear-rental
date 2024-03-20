package com.example.demo.Containers;

public class Administrator extends Employee{
    public Administrator(int id, String name, String surname, String telephoneNumber, String email, String idNumber, String login, String password, Position position, Adress adress) {
        super(id, name, surname, telephoneNumber, email, idNumber, login, password, position, adress);
    }

    @Override
    public boolean checkAuth(){
        return true;
    }
}
