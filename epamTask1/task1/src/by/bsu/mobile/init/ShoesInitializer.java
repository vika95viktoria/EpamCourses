package by.bsu.mobile.init;

import by.bsu.mobile.entity.Shoes;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class ShoesInitializer implements AbstractTariffInitializer<Shoes> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_FOR_FAVOURITE = 4;
    private final static int INDEX_OF_SMS = 5;
    private final static int INDEX_OF_TRAFFIC = 6;

    static Logger logger = Logger.getLogger(Shoes.class);

    public Shoes initialize(Shoes shoes, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            shoes.setTariffId(info.get(INDEX_OF_ID));
            shoes.setPayment(info.get(INDEX_OF_PAYMENT));
            shoes.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            shoes.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            shoes.setMinutesForFavourite(info.get(INDEX_OF_MINUTES_FOR_FAVOURITE));
            shoes.setSms(info.get(INDEX_OF_SMS));
            shoes.setTraffic(info.get(INDEX_OF_TRAFFIC));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return shoes;
    }
}
