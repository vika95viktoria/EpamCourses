package by.bsu.mobile.report;

import by.bsu.mobile.entity.AbstractTariff;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 25.11.2015.
 */
public class Reporter {
    static Logger logger = Logger.getLogger(Reporter.class);

    public static void printList(ArrayList<AbstractTariff> tariffs) {
        System.out.println();
        for (AbstractTariff tariff : tariffs) {
            System.out.println(tariff.getClass().getSimpleName() + ": " + tariff);
        }
    }

    public static void printNumberOfClients(int numberOfClients) {
        System.out.println("Number of clients: " + numberOfClients);
    }
}
