package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class SmartTariff extends UsualTariff {
    private int minutesInNet;
    private int traffic;

    public SmartTariff(int clients, int payment, int favouriteNumber, int minutesInNet, int traffic) {
        super(clients, payment, favouriteNumber);
        this.minutesInNet = minutesInNet;
        this.traffic = traffic;
    }

    public SmartTariff() {
    }

    public int getMinutesInNet() {
        return minutesInNet;
    }

    public void setMinutesInNet(int minutesInNet) {
        this.minutesInNet = minutesInNet;
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return super.toString() +
                "minutes in net=" + minutesInNet +
                ", traffic=" + traffic +
                ' ';
    }
}
