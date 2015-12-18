package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class Business extends AbstractTariff {
    private int traffic;
    private int minutesInNet;

    public Business(int clients, int payment, int traffic, int minutesInNet) {
        super(clients, payment);
        this.traffic = traffic;
        this.minutesInNet = minutesInNet;
    }

    public Business() {
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
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
                "traffic=" + traffic +
                ", minutes in net=" + minutesInNet +
                ", ";
    }
}
