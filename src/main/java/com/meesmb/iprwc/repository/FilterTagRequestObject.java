package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.FilterGroup;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class FilterTagRequestObject {
    String name;

    String filterGroup;

    public FilterTagRequestObject(String name, String filterGroupName) {
        this.name = name;
        this.filterGroup = filterGroupName;
    }

    public FilterTagRequestObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilterGroup() {
        return filterGroup;
    }

    public void setFilterGroupName(String filterGroup) {
        this.filterGroup = filterGroup;
    }
}
