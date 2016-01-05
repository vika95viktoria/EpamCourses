package by.bsu.xml.main;

/**
 * Created by Виктория on 22.12.2015.
 */

import by.bsu.xml.action.TariffMarshal;
import by.bsu.xml.action.ValidatorSAXXSD;
import by.bsu.xml.builder.AbstractTariffsBuilder;
import by.bsu.xml.builder.TariffsDOMBuilder;
import by.bsu.xml.builder.TariffsSAXBuilder;
import by.bsu.xml.builder.TariffsStAXBuilder;
import by.bsu.xml.entity.Tariff;
import by.bsu.xml.reporter.TariffReporter;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;

public class Main {
    static {
        PropertyConfigurator.configure("resources/log4j.properties");
    }

    public static void main(String[] args) {
        String fileName = "data/tariffs.xml";
        String schemaName = "data/tariffs.xsd";
        ValidatorSAXXSD.validate(fileName, schemaName);
        ArrayList<AbstractTariffsBuilder> builders = new ArrayList<>();
        TariffsDOMBuilder tariffsDOMBuilder = new TariffsDOMBuilder();
        TariffsSAXBuilder tariffsSAXBuilder = new TariffsSAXBuilder();
        TariffsStAXBuilder tariffsStAXBuilder = new TariffsStAXBuilder();
        builders.add(tariffsDOMBuilder);
        builders.add(tariffsSAXBuilder);
        builders.add(tariffsStAXBuilder);
        for(AbstractTariffsBuilder builder: builders){
            builder.buildTariffs(fileName);
            TariffReporter.printTariffs(builder.getTariffs(),builder.getClassName());
        }
        TariffMarshal.marshalTariff();
    }
}
