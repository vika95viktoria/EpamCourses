package by.bsu.mobile.init;

import by.bsu.mobile.entity.EverybodyTalks;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class EverybodyTalksInitializer implements AbstractTariffInitializer<EverybodyTalks> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;
    private final static int INDEX_OF_MINUTES_FOR_FAVOURITE = 5;

    static Logger logger = Logger.getLogger(EverybodyTalks.class);

    public EverybodyTalks initialize(EverybodyTalks everybodyTalks, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            everybodyTalks.setTariffId(info.get(INDEX_OF_ID));
            everybodyTalks.setPayment(info.get(INDEX_OF_PAYMENT));
            everybodyTalks.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            everybodyTalks.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            everybodyTalks.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
            everybodyTalks.setMinutesForFavourite(info.get(INDEX_OF_MINUTES_FOR_FAVOURITE));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return everybodyTalks;
    }
}
