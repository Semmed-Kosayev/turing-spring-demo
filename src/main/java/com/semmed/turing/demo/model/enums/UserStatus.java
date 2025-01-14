package com.semmed.turing.demo.model.enums;

public enum UserStatus {
    ACTIVE, INACTIVE, DELETED;

    public boolean exists(String status) {
        for (UserStatus value : values()) {
            if (value.name().equalsIgnoreCase(status))
                return true;
        }

        return false;
    }
}
