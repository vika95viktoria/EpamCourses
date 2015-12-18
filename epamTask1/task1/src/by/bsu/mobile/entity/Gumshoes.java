package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class Gumshoes extends Shoes {
    private int mms;

    public Gumshoes(int clients, int payment, int favouriteNumber, int minutesForFavourite, int sms, int traffic, int mms) {
        super(clients, payment, favouriteNumber, minutesForFavourite, sms, traffic);
        this.mms = mms;
    }

    public Gumshoes() {
    }

    public int getMms() {
        return mms;
    }

    public void setMms(int mms) {
        this.mms = mms;
    }

    @Override
    public String toString() {
        return super.toString() +
                "mms=" + mms +
                ' ';
    }
}
