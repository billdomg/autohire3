package com.carrental.autohire.service;

import com.carrental.autohire.config.DatabaseConfig;
import com.carrental.autohire.dto.CarRequestDto;
import com.carrental.autohire.dto.CarResponseDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {

    public CarResponseDto addCar(CarRequestDto carRequestDto) {

        String insertQuery = "INSERT INTO car (manufacturer, model, year, color, price) VALUES (?, ?, ?, ?,?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, carRequestDto.getManufacturer());
            preparedStatement.setString(2, carRequestDto.getModel());
            preparedStatement.setInt(3, carRequestDto.getYear());
            preparedStatement.setString(4, carRequestDto.getColor());
            preparedStatement.setLong(5,carRequestDto.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                Long carId = null;
                if (generatedKeys.next()) {
                    carId = generatedKeys.getLong(1);
                }

                log.info("Car added with ID: {}", carId);

                return new CarResponseDto(carId, carRequestDto.getManufacturer(), carRequestDto.getModel(),
                        carRequestDto.getYear(), carRequestDto.getColor(),carRequestDto.getPrice());
            } else {
                log.error("Failed to add car to the database");
            }

        } catch (SQLException e) {
            log.error("An error occurred while adding a car to the database", e);
        }

        return null;
    }

    public List<CarResponseDto> getAllCars() {

        List<CarResponseDto> cars = new ArrayList<>();

        String selectQuery = "SELECT * FROM car";

        try (Connection connection = DatabaseConfig.getConnection(); // without connection pooling
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long carId = resultSet.getLong("id");
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                String color = resultSet.getString("color");
                Long price = resultSet.getLong("price");
                Boolean isBooked = resultSet.getBoolean("is_booked");

                CarResponseDto car = new CarResponseDto(carId, manufacturer, model, year, color, price);
                car.setIsBooked(isBooked);
                cars.add(car);
            }

        } catch (SQLException e) {
            log.error("An error occurred while retrieving cars from the database", e);
        }

        log.info("Retrieved {} cars", cars.size());

        // cars = cars.stream().filter(carResponseDto -> carResponseDto.getIsBooked() == false).collect(Collectors.toList());
        
        cars = cars.stream()
           .filter(new Predicate<CarResponseDto>() {
               @Override
               public boolean test(CarResponseDto carResponseDto) {
                   return !carResponseDto.getIsBooked();
               }
           }).collect(Collectors.toList()); 

        return cars;
    }

    public boolean test(CarResponseDto carResponseDto) {
        return !carResponseDto.getIsBooked();
    }

    public CarResponseDto getCarById(Long carId) {
        String selectQuery = "SELECT * FROM car WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                String color = resultSet.getString("color");
                Long price = resultSet.getLong("price");

                CarResponseDto car = new CarResponseDto(carId, manufacturer, model, year, color,price);
                log.info("Retrieved car with ID {}: {}", carId, car);
                return car;
            }

        } catch (SQLException e) {
            log.error("An error occurred while retrieving the car with ID " + carId, e);
        }

        log.warn("Car with ID {} not found", carId);
        return null;
    }

    public String unregisterCarById(Long carId) {

        String updateQuery = "UPDATE car SET is_booked = ? WHERE id = ?";
        String deleteCustomerCarQuery = "DELETE FROM car_customer where car_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
             PreparedStatement preparedStatementCarCustomer = connection.prepareStatement(deleteCustomerCarQuery)) {
            
            connection.setAutoCommit(false);    // add autocommit
            
            preparedStatement.setBoolean(1, false);
            preparedStatement.setLong(2, carId);

            int rowsAffected = preparedStatement.executeUpdate();   // Update car

            preparedStatementCarCustomer.setLong(1, carId);
            
            try {   // enclose delete car_customer in a try
                preparedStatementCarCustomer.executeUpdate();   // Delete car_customer
            } catch(SQLException e) {   // if fails, rollback, log and return error msg
                connection.rollback();
                log.error("Error while deleting car_customer table.", carId);
                return "Error while deleting car_customer " + carId;
            }

            if (rowsAffected > 0) {
                connection.commit();    // if sucess, commit
                log.info("Car with ID {} updated successfully. is_booked set to true.", carId);
                return "Car updated successfully.";
            } else {
                log.warn("Car with ID {} not found.", carId);
                return "Car not found.";
            }

        } catch (SQLException e) {
            log.error("An error occurred while updating the car with ID " + carId, e);
            return "An error occurred while updating the car.";
        }
    }
}
