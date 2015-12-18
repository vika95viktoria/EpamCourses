package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class BusinessClass extends Business {
    private int sms;
    private int minutesInRouming;

    public BusinessClass() {
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getMinutesInRouming() {
        return minutesInRouming;
    }

    public void setMinutesInRouming(int minutesInRouming) {
        this.minutesInRouming = minutesInRouming;
    }


    @Override
    public String toString() {
        return super.toString() +
                "sms=" + sms +
                ", minutes in rouming=" + minutesInRouming +
                ' ';
    }
}
