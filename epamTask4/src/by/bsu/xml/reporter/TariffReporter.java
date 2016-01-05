package by.bsu.xml.reporter;

import by.bsu.xml.entity.Tariff;

import java.util.List;

/**
 * Created by Виктория on 05.01.2016.
 */
public class TariffReporter {
    public static void printTariffs(List<Tariff> tariffs, String name){
        System.out.println("Result of "+name);
        for (Tariff tariff : tariffs) {
            System.out.println(tariff);
        }
        System.out.println();
    }
}
