package com.mains.TemplateMethod;


public class TemplatePatternExample {
    public static void main(String[] args) {
        Abstract concrete1 = new Concrete1();
        concrete1.play();
        int n = 1;
        System.out.println(n);

        Abstract concrete2 = new Concrete2();
        concrete2.play();
    }
}