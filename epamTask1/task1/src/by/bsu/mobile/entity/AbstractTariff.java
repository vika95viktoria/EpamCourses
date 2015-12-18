package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */


public abstract class AbstractTariff {
    private int tariffId;
    private int payment;
    private int numberOfClients;

    public AbstractTariff(int numberOfClients, int payment) {
        this.numberOfClients = numberOfClients;
        this.payment = payment;
    }

    public AbstractTariff() {
    }

    @Override
    public String toString() {
        return "payment=" + payment +
                ", number of clients=" + numberOfClients + ", ";
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }
}
