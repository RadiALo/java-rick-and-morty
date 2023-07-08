package com.radialo.rickandmorty.model;

public enum Status {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
