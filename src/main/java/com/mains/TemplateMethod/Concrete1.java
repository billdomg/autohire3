package com.mains.TemplateMethod;

class Concrete1 extends Abstract {
    @Override
    void initialize() {
        System.out.println("Concrete1 Initialized");
    }

    @Override
    void startPlay() {
        System.out.println("Concrete1 Started");
    }

    @Override
    void endPlay() {
        System.out.println("Concrete1 Running");
    }
}
