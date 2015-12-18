package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class BusinessPro extends Business {
    private int mms;

    public BusinessPro(int clients, int payment, int traffic, int minutesInNet, int mms) {
        super(clients, payment, traffic, minutesInNet);
        this.mms = mms;
    }

    public BusinessPro() {
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
