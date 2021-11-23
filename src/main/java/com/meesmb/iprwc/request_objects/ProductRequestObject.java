package com.meesmb.iprwc.request_objects;

import com.meesmb.iprwc.model.FilterTag;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

public class ProductRequestObject {
    String name;

    float price;

    String description;

    String specs;

    String[] filterTags;

    String image = "";

    public ProductRequestObject(String name, float price, String description, String specs, String[] filterTags, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.specs = specs;
        this.filterTags = filterTags;
        this.image = image;
    }

    public ProductRequestObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String[] getFilterTags() {
        return filterTags;
    }

    public void setFilterTags(String[] filterTags) {
        this.filterTags = filterTags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
