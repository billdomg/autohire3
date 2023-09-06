package com.mains.CircuitBraker;
import com.netflix.hystrix.HystrixCommand;

public class CircuitBreakerExample {
    public static void main(String[] args) {
        // Create a Hystrix command with a circuit breaker
        HystrixCommand<String> command = new SampleBreaker();
        
        // Execute the command
        String result = command.execute();
        System.out.println("Result: " + result);
    }
}
