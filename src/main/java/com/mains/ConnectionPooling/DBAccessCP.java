package com.mains.ConnectionPooling;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccessCP {

    public static void main(String[] args) {
        // Configure HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/autohire");
        config.setUsername("autohire");
        config.setPassword("autohire");

        // Create a data source using HikariCP
        HikariDataSource dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            // Perform database operations using the connection
            String sql = "SELECT * FROM customer";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("id");
                    String name = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    System.out.println("Customer ID: " + customerId + ", Customer Name: " + name + " " + lastName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the data source to release resources
            dataSource.close();
        }
    }
}