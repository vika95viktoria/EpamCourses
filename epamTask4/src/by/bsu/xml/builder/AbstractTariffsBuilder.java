package by.bsu.xml.builder;

import by.bsu.xml.entity.Tariff;

import java.util.List;

/**
 * Created by Виктория on 05.01.2016.
 */
public interface AbstractTariffsBuilder {
    void buildTariffs(String fileName);
    List<Tariff> getTariffs();
    String getClassName();
}
