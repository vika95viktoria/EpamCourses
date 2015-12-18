package by.bsu.mobile.action;

import by.bsu.mobile.entity.AbstractTariff;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class CalculateClients {
    public static int calculateNumberOfClients(ArrayList<AbstractTariff> tariffs) {
        int count = 0;
        for (AbstractTariff tariff : tariffs) {
            count += tariff.getNumberOfClients();
        }
        return count;

    }
}
