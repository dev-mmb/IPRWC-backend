package com.meesmb.iprwc.request_objects;

public class AccountRequestObject {
    String email;
    String password;

    public AccountRequestObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountRequestObject() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
