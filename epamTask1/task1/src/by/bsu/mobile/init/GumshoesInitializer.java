package by.bsu.mobile.init;

import by.bsu.mobile.entity.Gumshoes;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class GumshoesInitializer implements AbstractTariffInitializer<Gumshoes> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_FOR_FAVOURITE = 4;
    private final static int INDEX_OF_SMS = 5;
    private final static int INDEX_OF_TRAFFIC = 6;
    private final static int INDEX_OF_MMS = 7;

    static Logger logger = Logger.getLogger(Gumshoes.class);

    public Gumshoes initialize(Gumshoes gumshoes, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            gumshoes.setTariffId(info.get(INDEX_OF_ID));
            gumshoes.setPayment(info.get(INDEX_OF_PAYMENT));
            gumshoes.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            gumshoes.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            gumshoes.setMinutesForFavourite(info.get(INDEX_OF_MINUTES_FOR_FAVOURITE));
            gumshoes.setSms(info.get(INDEX_OF_SMS));
            gumshoes.setTraffic(info.get(INDEX_OF_TRAFFIC));
            gumshoes.setMms(info.get(INDEX_OF_MMS));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return gumshoes;
    }
}
