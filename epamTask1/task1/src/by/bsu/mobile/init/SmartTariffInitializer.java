package by.bsu.mobile.init;

import by.bsu.mobile.entity.SmartTariff;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class SmartTariffInitializer implements AbstractTariffInitializer<SmartTariff> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;
    private final static int INDEX_OF_TRAFFIC = 5;

    static Logger logger = Logger.getLogger(SmartTariff.class);

    public SmartTariff initialize(SmartTariff smartTariff, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            smartTariff.setTariffId(info.get(INDEX_OF_ID));
            smartTariff.setPayment(info.get(INDEX_OF_PAYMENT));
            smartTariff.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            smartTariff.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            smartTariff.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
            smartTariff.setTraffic(info.get(INDEX_OF_TRAFFIC));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return smartTariff;
    }
}
