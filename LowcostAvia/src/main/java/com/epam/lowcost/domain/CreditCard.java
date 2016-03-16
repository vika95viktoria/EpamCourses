package com.epam.lowcost.domain;

/**
 * Created by Виктория on 17.02.2016.
 */
public class CreditCard extends Entity {
    private long id;
    private double amount;
    private String type;

    public CreditCard() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
