package com.meesmb.iprwc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FilterGroup {
    @Id
    @Column(name = "name")
    String name;

    public FilterGroup(String name) {
        this.name = name;
    }

    public FilterGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
