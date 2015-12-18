package by.bsu.mobile.init;

import by.bsu.mobile.entity.AbstractTariff;

/**
 * Created by Викторианец on 25.11.2015.
 */
public interface AbstractTariffInitializer<T extends AbstractTariff> {
     T initialize(T t, String fileName);
}
