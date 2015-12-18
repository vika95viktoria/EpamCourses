package by.bsu.mobile.init;

import by.bsu.mobile.entity.InConnection;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class InConnectionInitializer implements AbstractTariffInitializer<InConnection> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_FAVOURITE_NUMBER = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;

    static Logger logger = Logger.getLogger(InConnection.class);

    public InConnection initialize(InConnection inConnection, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            inConnection.setTariffId(info.get(INDEX_OF_ID));
            inConnection.setPayment(info.get(INDEX_OF_PAYMENT));
            inConnection.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            inConnection.setCountOfFavourites(info.get(INDEX_OF_FAVOURITE_NUMBER));
            inConnection.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }

        return inConnection;
    }
}
