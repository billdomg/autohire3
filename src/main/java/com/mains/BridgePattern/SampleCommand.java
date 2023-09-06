package com.mains.BridgePattern;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
class SampleCommand extends HystrixCommand<String> {
    SampleCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("SampleGroup"));
    }

    @Override
    protected String run() {
        // Simulate a service call
        double randNum = Math.random();
        System.out.println(randNum);
        if (randNum > 0.5) {
            throw new RuntimeException("Service unavailable");
        }
        return "Service response";
    }

    @Override
    protected String getFallback() {
        return "Fallback response";
    }
}
