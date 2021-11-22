package com.meesmb.iprwc.http_response;

public enum HTTPResponseCode {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private String value;

    private HTTPResponseCode(String val) {
        this.value = val;
    }

    public String toString() { return value; }

    public boolean equals(HTTPResponseCode other) {
        return other.toString().equals(value);
    }
}
