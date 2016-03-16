package com.epam.lowcost.domain;

/**
 * Created by Виктория on 17.02.2016.
 */
public class Route extends Entity {
    private long id;
    private City cityFrom;
    private City cityTo;

    public Route() {
    }

    public Route(long id, City cityFrom, City cityTo) {
        this.id = id;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }
}
