package com.epam.lowcost.domain;

/**
 * Created by Виктория on 22.02.2016.
 */
public class City extends Entity {
    private long id;
    private String name;

    public City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
