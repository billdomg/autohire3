package com.mains.Animals;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        new Main();
    }

    public Main() {

        // List<Animal> animalList = new ArrayList<>(); // A list of dogs and Cats
        List<Domestic> domesticList = new ArrayList<>(); // A list of dogs and Cats
        List<Wild> wildList = new ArrayList<>(); // A list of Lions and Zebras
        List<Cat> catList = new ArrayList<>(); // A list of cat onlys but with Angoras and Persians
        List<Dog> dogList = new ArrayList<>();
        //List<Animal> animalList = new ArrayList<>();

        domesticList.add(0, new Domestic());
        domesticList.add(1, new Cat());
        domesticList.add(2, new Dog());
        wildList.add(0, new Zebra());
        catList.add(0, new Cat());
        dogList.add(0, new Dog());

        setDomestic1(domesticList); // over 500 refereces in hundred of classes
        setDomestic2(dogList); 
        setDomestic3(domesticList);

        // setDomestic1(catList);                            // Problem, a class does not have a domesticList but a catList with dozens of references

    }

    private void setDomestic1(List <Domestic> domesticList) // Generics 
    {   
        if (domesticList instanceof Cat)
            ((Cat) domesticList.get(0)).doMeaw();
        if (domesticList instanceof Dog)
            ((Dog) domesticList.get(0)).barf();
    }                                                   // List with Domestic
    private void setDomestic2(List <? extends Domestic> domesticList) // Generecs & lower bound wildcards
    {   }                                                          // List with Domestic or lower classes
    private void setDomestic3(List <? super Domestic> domesticList) // Generics & upper bound wildcards
    {   }                                                        // List with Domestic or upper classes 

}
