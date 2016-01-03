package by.bsu.xml.main;

/**
 * Created by Виктория on 22.12.2015.
 */

import by.bsu.xml.action.ValidatorSAXXSD;
import by.bsu.xml.builder.TariffsDOMBuilder;
import by.bsu.xml.builder.TariffsSAXBuilder;
import by.bsu.xml.builder.TariffsStAXBuilder;
import by.bsu.xml.entity.Tariff;
import org.apache.log4j.PropertyConfigurator;

public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    public static void main(String[] args) {
        String fileName = "data/tariffs.xml";
        String schemaName = "data/tariffs.xsd";
        ValidatorSAXXSD.validate(fileName, schemaName);
        TariffsDOMBuilder tariffsDOMBuilder = new TariffsDOMBuilder();
        tariffsDOMBuilder.buildTariffs(fileName);
        TariffsSAXBuilder tariffsSAXBuilder = new TariffsSAXBuilder();
        tariffsSAXBuilder.buildTariffs(fileName);
        TariffsStAXBuilder tariffsStAXBuilder = new TariffsStAXBuilder();
        tariffsStAXBuilder.buildTariffs(fileName);
        for (Tariff tariff : tariffsStAXBuilder.getTariffs()) {
            System.out.println(tariff);
        }
    }
}
