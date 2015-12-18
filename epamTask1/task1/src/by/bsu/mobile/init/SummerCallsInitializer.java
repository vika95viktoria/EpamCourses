package by.bsu.mobile.init;

import by.bsu.mobile.entity.SummerCalls;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class SummerCallsInitializer implements AbstractTariffInitializer<SummerCalls> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_FOR_FAVOURITE = 4;

    static Logger logger = Logger.getLogger(SummerCalls.class);

    public SummerCalls initialize(SummerCalls summerCalls, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            summerCalls.setTariffId(info.get(INDEX_OF_ID));
            summerCalls.setPayment(info.get(INDEX_OF_PAYMENT));
            summerCalls.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            summerCalls.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            summerCalls.setMinutesForFavourites(info.get(INDEX_OF_MINUTES_FOR_FAVOURITE));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return summerCalls;
    }
}
