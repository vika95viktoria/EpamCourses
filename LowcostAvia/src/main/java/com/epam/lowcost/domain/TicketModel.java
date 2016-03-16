package com.epam.lowcost.domain;

import java.util.List;

/**
 * Created by Виктория on 03.03.2016.
 */
public class TicketModel {
    private List<String> names;
    private List<String> surnames;
    private Integer luggage;
    private Integer luggageCount;
    private Integer priorityCount;
    private Long flightId;
    private Boolean isBusiness;
    private Double price;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getSurnames() {
        return surnames;
    }

    public void setSurnames(List<String> surnames) {
        this.surnames = surnames;
    }

    public Integer getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Integer luggageCount) {
        this.luggageCount = luggageCount;
    }

    public Integer getLuggage() {
        return luggage;
    }

    public void setLuggage(Integer luggage) {
        this.luggage = luggage;
    }

    public Integer getPriorityCount() {
        return priorityCount;
    }

    public void setPriorityCount(Integer priorityCount) {
        this.priorityCount = priorityCount;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Boolean getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Boolean business) {
        isBusiness = business;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
