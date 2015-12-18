package by.bsu.mobile.init;

import by.bsu.mobile.entity.Business;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class BusinessInitializer implements AbstractTariffInitializer<Business> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_TRAFFIC = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;

    static Logger logger = Logger.getLogger(BusinessInitializer.class);

    public Business initialize(Business business, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            business.setTariffId(info.get(INDEX_OF_ID));
            business.setPayment(info.get(INDEX_OF_PAYMENT));
            business.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            business.setTraffic(info.get(INDEX_OF_TRAFFIC));
            business.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }
        return business;
    }
}
