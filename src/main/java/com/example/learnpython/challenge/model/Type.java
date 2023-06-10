package com.example.learnpython.challenge.model;

public enum Type {

    CLOSED(100),
    OPEN(200),
    CODE(300);

    private final int expGained;

    Type(int expGained) {
        this.expGained = expGained;
    }

    public int getExpGained() {
        return expGained;
    }
}
