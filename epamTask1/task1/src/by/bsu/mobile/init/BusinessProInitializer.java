package by.bsu.mobile.init;

import by.bsu.mobile.entity.BusinessPro;
import by.bsu.mobile.exception.InvalidTariffDataException;
import by.bsu.mobile.service.ReadFileService;
import by.bsu.mobile.util.DataChecker;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class BusinessProInitializer implements AbstractTariffInitializer<BusinessPro> {
    private final static int INDEX_OF_ID = 0;
    private final static int INDEX_OF_PAYMENT = 1;
    private final static int INDEX_OF_CLIENTS = 2;
    private final static int INDEX_OF_TRAFFIC = 3;
    private final static int INDEX_OF_MINUTES_IN_NET = 4;
    private final static int INDEX_OF_MMS = 5;

    static Logger logger = Logger.getLogger(BusinessPro.class);

    public BusinessPro initialize(BusinessPro businessPro, String fileName) {
        ArrayList<Integer> info = ReadFileService.readInfo(fileName);
        try {
            DataChecker.checkList(info);
            businessPro.setTariffId(info.get(INDEX_OF_ID));
            businessPro.setPayment(info.get(INDEX_OF_PAYMENT));
            businessPro.setNumberOfClients(info.get(INDEX_OF_CLIENTS));
            businessPro.setTraffic(info.get(INDEX_OF_TRAFFIC));
            businessPro.setMinutesInNet(info.get(INDEX_OF_MINUTES_IN_NET));
            businessPro.setMms(info.get(INDEX_OF_MMS));
        } catch (InvalidTariffDataException e) {
            logger.error(e);
        }
        return businessPro;
    }
}
