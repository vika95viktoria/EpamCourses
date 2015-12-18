package by.bsu.mobile.main;

import by.bsu.mobile.action.CalculateClients;
import by.bsu.mobile.action.SortingTariffes;
import by.bsu.mobile.action.TariffSearcher;
import by.bsu.mobile.creator.ListOfTariffsCreator;
import by.bsu.mobile.entity.AbstractTariff;
import by.bsu.mobile.report.Reporter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */

public class MobileNetwork {
    static Logger logger = Logger.getLogger(MobileNetwork.class);

    static {
        PropertyConfigurator.configure("task1/resources/log4j.properties");
    }

    public static void main(String[] args) {
        ArrayList<AbstractTariff> tariffs = SortingTariffes.sortTariffesByPayment(ListOfTariffsCreator.createList());
        Reporter.printList(tariffs);
        int numberOfClients = CalculateClients.calculateNumberOfClients(tariffs);
        Reporter.printNumberOfClients(numberOfClients);
        ArrayList<AbstractTariff> cheap = TariffSearcher.searchByPayment(tariffs, 45000);
        Reporter.printList(cheap);

    }
}
