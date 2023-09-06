package com.mains.Reflection;

public final class TheClass implements ClassInterface {
    private int number;
    private String name;

    public TheClass() {
        
    }
    public int getId() {
        return 1;
    }
    @Override
    public int getNumber() {
        number = 10;
        return number;
    }
    @Override
    public String getName() {
        name = "John";
        return name;
    }

}