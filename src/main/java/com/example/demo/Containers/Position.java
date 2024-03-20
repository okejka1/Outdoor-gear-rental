package com.example.demo.Containers;

public class Position {
    private final int id;
    private String name;

    public Position(int id) {
        this.id = id;
        if(id == 1){
            name ="Prezes";
        }
        else{
            name = "ekspedient";
        }
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
}
