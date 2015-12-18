package by.bsu.mobile.action;

import by.bsu.mobile.entity.AbstractTariff;
import by.bsu.mobile.util.PaymentComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class SortingTariffes {
    public static ArrayList<AbstractTariff> sortTariffesByPayment(ArrayList<AbstractTariff> tariffs) {
        Collections.sort(tariffs, new PaymentComparator());
        return tariffs;
    }
}
