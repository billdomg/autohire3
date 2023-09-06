package com.carrental.autohire.service;

import com.carrental.autohire.config.DatabaseConfig;
//import com.carrental.autohire.dto.AbstractCarDto;
import com.carrental.autohire.dto.BookVehicleDto;
//import com.carrental.autohire.dto.CarRequestDto;
import com.carrental.autohire.dto.CarResponseDto;
import com.carrental.autohire.dto.CustomerResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;

import java.io.File;
import java.util.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
// import java.util.concurrent.atomic.DoubleAdder;

@Service
@Slf4j
public class CustomerService {

    public final String senderEmail = "hireauto448@gmail.com";

    public final String senderPassword = "zvegpmkmmqcarmvr";

    public CustomerResponseDto bookCar(BookVehicleDto bookVehicleDto) {
        String selectQueryCustomerByEmail = "SELECT id FROM customer WHERE email = ?";
        String insertCustomerQuery = "INSERT INTO customer (first_name, last_name, email) VALUES (?, ?, ?)";
        String insertCustomerCarQuery = "INSERT INTO car_customer (customer_id, car_id) VALUES (?, ?)";
        String updateCarQuery = "UPDATE car set is_booked=? where id=?";

        try (Connection connection = DatabaseConfig.getConnection(); 
             PreparedStatement selectStatementByEmail = connection.prepareStatement(selectQueryCustomerByEmail);
             PreparedStatement insertCustomerStatement = connection.prepareStatement(insertCustomerQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement insertCustomerCarStatement = connection.prepareStatement(insertCustomerCarQuery);
             PreparedStatement carStatement = connection.prepareStatement(updateCarQuery)) {
            
            // connection.setAutoCommit(false); // Dissable autoCommit

            selectStatementByEmail.setString(1, bookVehicleDto.getEmail());
            ResultSet resultSet = selectStatementByEmail.executeQuery(); // read customer table

            Long customerId;
            if (resultSet.next()) {
                customerId = resultSet.getLong("id");
            } else {
                insertCustomerStatement.setString(1, bookVehicleDto.getFirstName());
                insertCustomerStatement.setString(2, bookVehicleDto.getLastName());
                insertCustomerStatement.setString(3, bookVehicleDto.getEmail());


                int rowsAffected = insertCustomerStatement.executeUpdate(); // insert customer table
                if (rowsAffected == 1) {
                    ResultSet generatedKeys = insertCustomerStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        customerId = generatedKeys.getLong(1);
                    } else {
                        log.error("Failed to retrieve customer ID after insertion");
                        // connection.rollback();  // Roll back because of error
                        return null;
                    }
                } else {
                    log.error("Failed to add customer to the database");
                    // connection.rollback();  // Roll back because of error
                    return null;
                }
            }

            Long carId = bookVehicleDto.getCarId();
            insertCustomerCarStatement.setLong(1, customerId);
            insertCustomerCarStatement.setLong(2, carId);

            int rowsAffected = insertCustomerCarStatement.executeUpdate(); // insert car_customer
            //***********************************************************************************************************
            if (rowsAffected == 1) {
                log.info("Added entry to car_customer table for Customer ID: {} and Car ID: {}", customerId, carId);
            } else {
                log.error("Failed to add entry to car_customer table");
                // connection.rollback(); // Roll back because of error
                return null;
            }

            carStatement.setBoolean(1, true);
            carStatement.setLong(2, carId);
            carStatement.executeUpdate(); // update car

            // connection.commit(); // commit transaction

            createAndSendReceipt(customerId, carId, bookVehicleDto.getEmail());

            return new CustomerResponseDto(customerId, bookVehicleDto.getFirstName(), bookVehicleDto.getLastName(), bookVehicleDto.getEmail());
        } catch (SQLException e) {
            log.error("An error occurred while adding a customer to the database", e);
        }

        return null;
    }

    private void createAndSendReceipt(Long customerCarId, Long carId, String email) throws SQLException {
        String selectCarQuery = "SELECT manufacturer, model, price FROM car WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement selectCarStatement = connection.prepareStatement(selectCarQuery)) {

            selectCarStatement.setLong(1, carId);
            ResultSet resultSet = selectCarStatement.executeQuery();

                if (resultSet.next()) {
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    double price = resultSet.getDouble("price");

                    double taxes = price * 0.15;
                    double total = price + taxes;

                    NumberFormat currencyFormatter = new DecimalFormat("#0.00");
                    String formattedPrice = currencyFormatter.format(price);
                    String formattedTaxes = currencyFormatter.format(taxes);
                    String formattedTotal = currencyFormatter.format(total);

                    // Generate PDF receipt
                    String receiptFileName = "receipt_" + customerCarId + ".pdf";

                    try {
                        ITextRenderer renderer = new ITextRenderer();

                        StringBuilder htmlContent = new StringBuilder();
                        htmlContent.append("<html><head><style>");
                        htmlContent.append("body { font-family: Arial, sans-serif; }");
                        htmlContent.append("h1 { font-size: 20px; }");
                        htmlContent.append("p { font-size: 12px; }");
                        htmlContent.append("</style></head><body>");
                        htmlContent.append("<h1>AutoHire Car Rental - Receipt</h1>");
                        htmlContent.append("<p>Manufacturer: ").append(manufacturer).append("</p>");
                        htmlContent.append("<p>Model: ").append(model).append("</p>");
                        htmlContent.append("<p>Price: $").append(formattedPrice).append("</p>");
                        htmlContent.append("<p>Taxes: $").append(formattedTaxes).append("</p>");
                        htmlContent.append("<p>Total: $").append(formattedTotal).append("</p>");
                        htmlContent.append("</body></html>");

                        renderer.setDocumentFromString(htmlContent.toString());

                        try (OutputStream outputStream = new FileOutputStream(receiptFileName)) {
                            renderer.layout();
                            renderer.createPDF(outputStream);
                        }
                        String subject = "AutoHire Car Rental - Receipt";
                        String body = "Dear Customer,\n\nPlease find attached the receipt for your car rental from AutoHire Car Rental.\n\nBest regards,\nAutoHire Car Rental";
                        
                        Runnable emailTask = () -> sendEmail(email, subject, body, receiptFileName);
                        Thread emailThread = new Thread(emailTask);
                        emailThread.start();
                        //sendEmail(email, subject, body, receiptFileName);

                        log.info("Receipt created for CustomerCar ID: {}, Car ID: {}", customerCarId, carId);
                        log.info("Receipt sent to customer with Email: {}", email);
                    } catch (Exception e) {
                        log.error("An error occurred while generating the PDF receipt", e);
                    }
                }
            }
        }

    private void sendEmail(String recipientEmail, String subject, String body, String attachmentPath) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            pdfAttachment.setDataHandler(new DataHandler(source));
            pdfAttachment.setFileName(new File(attachmentPath).getName());

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(pdfAttachment);

            message.setContent(multipart);

            Transport.send(message);

            log.info("Email sent to recipient: {}", recipientEmail);
        } catch (MessagingException e) {
            log.error("An error occurred while sending the email", e);
        }
    }


    public List<CustomerResponseDto> getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();

        String selectQuery = "SELECT * FROM customer";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long customerId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");

                CustomerResponseDto customer = new CustomerResponseDto(customerId, firstName, lastName, email);
                customers.add(customer);
            }

        } catch (SQLException e) {
            log.error("An error occurred while retrieving customers from the database", e);
        }

        log.info("Retrieved {} customers", customers.size());

        return customers;
    }

    public CustomerResponseDto getCustomerById(Long customerId) {
        String selectQuery = "SELECT * FROM customer WHERE id = ?";
        String carCustomerQuery = "SELECT car_id FROM car_customer where customer_id=?";
        String selectCarQuery = "SELECT * FROM car WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            PreparedStatement preparedStatementCarQuery = connection.prepareStatement(carCustomerQuery);
            PreparedStatement carSelectStatementQuery = connection.prepareStatement(selectCarQuery);
            preparedStatementCarQuery.setLong(1,customerId);
            ResultSet resultSetCarId = preparedStatementCarQuery.executeQuery();
            Set<Long> carIds = new HashSet<>();

            List<CarResponseDto> carResponseDtos = new ArrayList<>();

            while(resultSetCarId.next()){
                Long carId = resultSetCarId.getLong("car_id");
                carIds.add(carId);
            }
            carIds.forEach(carId-> {
                try {
                    carSelectStatementQuery.setLong(1,carId);
                    ResultSet carResultSet = carSelectStatementQuery.executeQuery();
                    if (carResultSet.next()) {
                        String manufacturer = carResultSet.getString("manufacturer");
                        String model = carResultSet.getString("model");
                        int year = carResultSet.getInt("year");
                        String color = carResultSet.getString("color");
                        Long price = carResultSet.getLong("price");

                        CarResponseDto car = new CarResponseDto(carId, manufacturer, model, year, color,price);
                        carResponseDtos.add(car);
                        log.info("Retrieved car with ID {}: {}", carId, car);

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");

                CustomerResponseDto customer = new CustomerResponseDto(customerId, firstName, lastName, email,carResponseDtos);

                double totalPrice = 0.0;
                List<CarResponseDto> cars = (List<CarResponseDto>) customer.getCarList();
                
                for (CarResponseDto car : cars) {
                    totalPrice += car.getPrice();
                } 
                
                customer.setTotalExpense(totalPrice); 
                

                log.info("Retrieved customer with ID {}: {}", customerId, customer);
                return customer;
            }

        } catch (SQLException e) {
            log.error("An error occurred while retrieving the customer with ID " + customerId, e);
        }

        log.warn("Customer with ID {} not found", customerId);
        return null;
    }

}
