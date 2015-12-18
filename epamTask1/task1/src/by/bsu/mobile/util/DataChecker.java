package by.bsu.mobile.util;

import by.bsu.mobile.exception.InvalidTariffDataException;

import java.util.ArrayList;

/**
 * Created by Викторианец on 26.11.2015.
 */
public class DataChecker {
    public static void checkList(ArrayList<Integer> list) throws InvalidTariffDataException {
        for (Integer a : list) {
            if (a < 0) {
                throw new InvalidTariffDataException();
            }
        }
    }
}
