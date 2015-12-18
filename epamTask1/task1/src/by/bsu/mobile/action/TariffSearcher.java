package by.bsu.mobile.action;

import by.bsu.mobile.entity.AbstractTariff;
import by.bsu.mobile.entity.Business;
import by.bsu.mobile.entity.UsualTariff;

import java.util.ArrayList;

/**
 * Created by Викторианец on 25.11.2015.
 */
public class TariffSearcher {
    public static ArrayList<AbstractTariff> searchByPayment(ArrayList<AbstractTariff> tariffs, int payment) {
        ArrayList<AbstractTariff> searchResults = new ArrayList<>();
        for (AbstractTariff tariff : tariffs) {
            if (tariff.getPayment() < payment) {
                searchResults.add(tariff);
            }
        }
        return searchResults;
    }

    public static ArrayList<Business> searchByTrafficInBusiness(ArrayList<Business> tariffs, int traffic) {
        ArrayList<Business> searchResults = new ArrayList<>();
        for (Business tariff : tariffs) {
            if (tariff.getTraffic() >= traffic) {
                searchResults.add(tariff);
            }
        }
        return searchResults;
    }

    public static ArrayList<UsualTariff> searchByFavourite(ArrayList<UsualTariff> tariffs, int favourite) {
        ArrayList<UsualTariff> searchResults = new ArrayList<>();
        for (UsualTariff tariff : tariffs) {
            if (tariff.getCountOfFavourites() >= favourite) {
                searchResults.add(tariff);
            }
        }
        return searchResults;
    }
}
