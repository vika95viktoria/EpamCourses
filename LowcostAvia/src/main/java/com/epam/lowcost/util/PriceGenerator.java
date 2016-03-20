package com.epam.lowcost.util;

import java.util.Date;

/**
 * Created by Виктория on 07.03.2016.
 */
public class PriceGenerator {
    private static long WEEK = 604800000;
    private static long MONTH = 2629746000L;
    private int purchasedTickets;
    private int remainedTickets;
    private Date dateOut;

    public PriceGenerator() {
    }

    public void setPurchasedTickets(int purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public void setRemainedTickets(int remainedTickets) {
        this.remainedTickets = remainedTickets;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    private double getPercentsFotDate() {
        double percents = 0;
        Date today = new Date();
        long remainingTime = dateOut.getTime() - today.getTime();
        if (remainingTime > MONTH) {
            percents = 0;
        } else if (remainingTime <= MONTH && remainingTime > 3 * WEEK) {
            percents = .1;
        } else if (remainingTime <= 3 * WEEK && remainingTime > 2 * WEEK) {
            percents = 0.25;
        } else if (remainingTime <= 2 * WEEK && remainingTime > WEEK) {
            percents = 0.4;
        } else if (remainingTime <= WEEK) {
            percents = 0.5;
        }
        return percents;
    }

    private double getPercentsFotFulness() {
        double percents = 0;
        double fulness = purchasedTickets / (purchasedTickets + remainedTickets);
        if (fulness < 0.25) {
            percents = 0;
        } else if (fulness >= 0.25 && fulness < 0.5) {
            percents = 0.05;
        } else if (fulness >= 0.5 && fulness < 0.75) {
            percents = 0.15;
        } else if (fulness > 0.75) {
            percents = 0.3;
        }
        return percents;
    }

    public double generatePrice(double defaultPrice) {
        double finalPrice = Math.round((getPercentsFotDate() + getPercentsFotFulness() + 1) * defaultPrice);
        return finalPrice;
    }
}
