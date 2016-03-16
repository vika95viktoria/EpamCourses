package com.epam.lowcost.domain;

import java.util.Date;

/**
 * Created by Виктория on 17.02.2016.
 */
public class Flight extends Entity {
    private long id;
    private Route route;
    private int businessCount;
    private int economyCount;
    private Date dateOut;
    private Date dateIn;
    private double businessPrice;
    private double economyPrice;

    public Flight(long id, Route route, int businessCount, int economyCount, Date dateOut, Date dateIn) {
        this.id = id;
        this.route = route;
        this.businessCount = businessCount;
        this.economyCount = economyCount;
        this.dateOut = dateOut;
        this.dateIn = dateIn;
    }

    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getBusinessCount() {
        return businessCount;
    }

    public void setBusinessCount(int businessCount) {
        this.businessCount = businessCount;
    }

    public int getEconomyCount() {
        return economyCount;
    }

    public void setEconomyCount(int economyCount) {
        this.economyCount = economyCount;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(double economyPrice) {
        this.economyPrice = economyPrice;
    }
}
