package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class Shoes extends UsualTariff {
    private int minutesForFavourite;
    private int sms;
    private int traffic;


    public Shoes(int clients, int payment, int favouriteNumber, int minutesForFavourite, int sms, int traffic) {
        super(clients, payment, favouriteNumber);
        this.minutesForFavourite = minutesForFavourite;
        this.sms = sms;
        this.traffic = traffic;
    }

    public Shoes() {
    }

    public int getMinutesForFavourite() {
        return minutesForFavourite;
    }

    public void setMinutesForFavourite(int minutesForFavourite) {
        this.minutesForFavourite = minutesForFavourite;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
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
                "minutes for favourite numbers=" + minutesForFavourite +
                ", sms=" + sms +
                ", traffic=" + traffic +
                ' ';
    }
}
