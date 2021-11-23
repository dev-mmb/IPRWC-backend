package com.meesmb.iprwc.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class FilterTag {
    @Id
    @Column(name = "name")
    String name;

    @ManyToOne()
    FilterGroup filterGroup;

    public FilterTag(String name, FilterGroup filterGroupName) {
        this.name = name;
        this.filterGroup = filterGroupName;
    }

    public FilterTag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FilterGroup getFilterGroup() {
        return filterGroup;
    }

    public void setFilterGroupName(FilterGroup filterGroup) {
        this.filterGroup = filterGroup;
    }
}
