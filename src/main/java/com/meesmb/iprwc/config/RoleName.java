package com.meesmb.iprwc.config;

public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;
    private RoleName(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }
}
