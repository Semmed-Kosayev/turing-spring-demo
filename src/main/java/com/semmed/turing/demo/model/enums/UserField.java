package com.semmed.turing.demo.model.enums;

public enum UserField {

    ID("id"),
    USERNAME("username"),
    EMAIL("email"),
    PASSWORD("password"),
    STATUS("status");

    private final String value;

    UserField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
