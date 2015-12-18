package by.bsu.mobile.entity;

/**
 * Created by Викторианец on 24.11.2015.
 */
public abstract class UsualTariff extends AbstractTariff {
    private int countOfFavourites;

    public UsualTariff(int clients, int payment, int countOfFavourites) {
        super(clients, payment);
        this.countOfFavourites = countOfFavourites;
    }

    public UsualTariff() {
    }

    @Override
    public String toString() {
        return super.toString() +
                "count of favourite numbers=" + countOfFavourites + ", ";
    }

    public int getCountOfFavourites() {
        return countOfFavourites;
    }

    public void setCountOfFavourites(int countOfFavourites) {
        this.countOfFavourites = countOfFavourites;
    }
}
