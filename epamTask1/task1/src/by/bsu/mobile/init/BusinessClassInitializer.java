package by.bsu.mobile.init;

import by.bsu.mobile.entity.BusinessClass;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class BusinessClassInitializer implements AbstractTariffInitializer<BusinessClass> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_TRAFFIC = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;
    private final static int INDEX_OF_SMS = 5;
    private final static int INDEX_OF_MINUTES_IN_ROUMING = 6;

    static Logger logger = Logger.getLogger(BusinessClass.class);

    public BusinessClass initialize(BusinessClass businessClass, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            businessClass.setTariffId(info.get(INDEX_OF_ID));
            businessClass.setPayment(info.get(INDEX_OF_PAYMENT));
            businessClass.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            businessClass.setTraffic(info.get(INDEX_OF_TRAFFIC));
            businessClass.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
            businessClass.setSms(info.get(INDEX_OF_SMS));
            businessClass.setMinutesInRouming(info.get(INDEX_OF_MINUTES_IN_ROUMING));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }
        return businessClass;
    }
}
