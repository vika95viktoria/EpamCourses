package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class InConnection extends UsualTariff {
    private int minutesInNet;

    public InConnection(int clients, int payment, int favouriteNumber, int minutesInNet) {
        super(clients, payment, favouriteNumber);
        this.minutesInNet = minutesInNet;
    }

    public InConnection() {
    }

    public int getMinutesInNet() {
        return minutesInNet;
    }

    public void setMinutesInNet(int minutesInNet) {
        this.minutesInNet = minutesInNet;
    }

    @Override
    public String toString() {
        return super.toString() +
                "minutes in net=" + minutesInNet +
                ' ';
    }
}
