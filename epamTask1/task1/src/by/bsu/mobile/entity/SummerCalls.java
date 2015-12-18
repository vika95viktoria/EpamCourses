package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class SummerCalls extends UsualTariff {
    private int minutesForFavourites;

    public SummerCalls(int clients, int payment, int favouriteNumber, int minutesForFavourites) {
        super(clients, payment, favouriteNumber);
        this.minutesForFavourites = minutesForFavourites;
    }

    public SummerCalls() {
    }

    public int getMinutesForFavourites() {
        return minutesForFavourites;
    }

    public void setMinutesForFavourites(int minutesForFavourites) {
        this.minutesForFavourites = minutesForFavourites;
    }

    @Override
    public String toString() {
        return super.toString() +
                "minutes for favourite numbers=" + minutesForFavourites +
                ' ';
    }
}
