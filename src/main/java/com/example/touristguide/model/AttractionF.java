package com.example.touristguide.model;

import java.util.ArrayList;
import java.util.List;

public class AttractionF {
    private String name;
    private String description;
    private String city;
    private List <String> tags = new ArrayList <>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}