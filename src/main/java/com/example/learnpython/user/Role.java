package com.example.learnpython.user;

import lombok.Data;

public enum Role {
    USER(1),
    ADMIN(4),
    MODERATOR(3),
    PRIVILEGED_USER(2);


    private final int value;

    public int getValue() {
        return value;
    }

    Role(int value) {
        this.value = value;
    }
}
