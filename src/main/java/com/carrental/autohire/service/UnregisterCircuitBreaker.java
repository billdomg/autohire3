// code test class
package com.carrental.autohire.service;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnregisterCircuitBreaker extends HystrixCommand<ResponseEntity<String>> {
    private CarService carService;
    private Long carId;
    public UnregisterCircuitBreaker(CarService carService, Long carId) {
        super(HystrixCommandGroupKey.Factory.asKey("CarGroup"));
        this.carService = carService;
        this.carId = carId;
    }

    @Override
    protected ResponseEntity<String> run() {
        // execute potentially risky situation
        return new ResponseEntity<>(carService.unregisterCarById(carId), HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<String> getFallback() {
        // Fallback response in case of failure
        return new ResponseEntity<>("Fallback Response", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}