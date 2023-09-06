package com.mains.TemplateMethod;

abstract class Abstract {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    // Template Method
    public final void play() {
        initialize();
        startPlay();
        endPlay();
        common();
    }

    public final void common() {
        System.out.println("Finished");
    }
}
