package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class EverybodyTalks extends InConnection {
    private int minutesForFavourite;

    public EverybodyTalks(int clients, int payment, int favouriteNumber, int minutesInNet, int minutesForFavourite) {
        super(clients, payment, favouriteNumber, minutesInNet);
        this.minutesForFavourite = minutesForFavourite;
    }

    public EverybodyTalks() {
    }

    public int getMinutesForFavourite() {
        return minutesForFavourite;
    }

    public void setMinutesForFavourite(int minutesForFavourite) {
        this.minutesForFavourite = minutesForFavourite;
    }

    @Override
    public String toString() {
        return super.toString() +
                "minutes for favourite numbers=" + minutesForFavourite +
                ' ';
    }
}
