package com.mains.Streams;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("John", 25));
        clients.add(new Client("Alice", 30));
        clients.add(new Client("Robert", 22));
        clients.add(new Client("Maria", 28));
        clients.add(new Client("Norma", 35));

        // Filter people whose age is greater than or equal to 30 using Lambda

        System.out.println("Filtered Clients Old Way:");
        List<Client> filteredCli = filterByAge(clients);
        filteredCli.forEach(client -> System.out.println(client.getName() + " - " + client.getAge()));

        System.out.println("Filtered Clients New Way:"); 
        // Without lambda expression
        /* List<Client> filteredClients = clients.stream().filter(new java.util.function.Predicate<Client>() {
            @Override
            public boolean test(Client client) {
                return client.getAge() >= 30;
            }
        }).collect(Collectors.toList()); */

        // With lambda expression
        List<Client> filteredClients = clients.stream()
            .filter(client -> client.getAge() >= 30)
            .collect(Collectors.toList()); 

        // Print the filtered people
        filteredClients.forEach(client -> System.out.println(client.getName() + " - " + client.getAge()));

    }

    private static List<Client> filterByAge(List<Client> clients) {

        List<Client> filteredClients = new ArrayList<>();
        for (Client client : clients) {
            if (client.getAge() >= 30) {
                 filteredClients.add(client);
            }
        }
        return filteredClients;
    }
}
