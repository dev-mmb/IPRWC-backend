package com.meesmb.iprwc.config;

public enum PrivilegeName {
    READ("READ"),
    WRITE("WRITE");

    private final String value;
    PrivilegeName(String val) {
        this.value = val;
    }

    String getValue() {
        return "PRIVILEGE_" + value;
    }
}
