package com.meesmb.iprwc.request_objects;


public class FilterGroupRequestObject {
    String name;

    public FilterGroupRequestObject(String name) {
        this.name = name;
    }

    public FilterGroupRequestObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
