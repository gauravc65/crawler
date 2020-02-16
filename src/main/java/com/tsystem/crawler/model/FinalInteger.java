package com.tsystem.crawler.model;

public class FinalInteger {
    private int counter;

    public FinalInteger(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return this.counter;
    }

    public void increment() {
        this.counter++;
    }

    public void decrement() {
        this.counter--;
    }
}
