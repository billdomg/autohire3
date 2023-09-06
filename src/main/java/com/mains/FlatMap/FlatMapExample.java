package com.mains.FlatMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapExample {
    public static void main(String[] args) {
        List<List<String>> listOfLists = Arrays.asList(
            Arrays.asList("Element 1", "Element 2", "Element 3"),
            Arrays.asList("Element 4", "Element 5", "Element 6"),
            Arrays.asList("Element 7", "Element 8", "Element 9")
        );

        // Using flatMap to transform and flatten the stream
        List<String> flattenedList = listOfLists.stream()
            .flatMap(list -> list.stream()) // Apply a function to each list and flatten
            .collect(Collectors.toList());

        System.out.println(flattenedList); 
    }
}
