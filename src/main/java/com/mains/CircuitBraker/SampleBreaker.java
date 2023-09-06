package com.mains.CircuitBraker;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

class SampleBreaker extends HystrixCommand<String> {
    SampleBreaker() {
        super(HystrixCommandGroupKey.Factory.asKey("SampleGroup"));
    }

    @Override
    protected String run() {
        return "Service response";
    }

    @Override
    protected String getFallback() {
        return "Fallback response";
    }
}
