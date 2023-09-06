package com.mains.Lambdas;
import java.util.ArrayList;
import java.util.List;
// import java.util.function.Consumer;

public class LambdaExample {
    public static void main(String[] args) {
        // Using Lambda as a function
        FunctionalCalculator functionalCalculator = (num1, num2) -> num1 * num2;
        System.out.println("Calculator Results: " + functionalCalculator.multiply(5, 10));

        // Create a list of integers
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        
        // Using a lambda one line expression with the forEach method
        System.out.print("Original numbers: ");
        numbers.forEach(number -> System.out.print(number + " "));
        
        // Using a lambda multi-line expression
        System.out.println("\nSquared numbers:");
        numbers.forEach(number -> {
            int squared = number * number;
            System.out.println(squared);
        });
    }
}
