package by.bsu.mobile.util;

import by.bsu.mobile.entity.AbstractTariff;

import java.util.Comparator;

/**
 * Created by Викторианец on 25.11.2015.
 */
public class PaymentComparator implements Comparator<AbstractTariff> {

    @Override
    public int compare(AbstractTariff tariff1, AbstractTariff tariff2) {
        return tariff1.getPayment() - tariff2.getPayment();
    }
}
