package com.mains.TemplateMethod;

class Concrete2 extends Abstract {
    @Override
    void initialize() {
        System.out.println("Concrete2 Initialized");
    }

    @Override
    void startPlay() {
        System.out.println("Concrete2 Started");
    }

    @Override
    void endPlay() {
        System.out.println("Concrete2 Running");
    }
}
