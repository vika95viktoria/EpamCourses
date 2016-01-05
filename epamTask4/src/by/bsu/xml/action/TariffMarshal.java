package by.bsu.xml.action;

import by.bsu.xml.entity.CallPrices;
import by.bsu.xml.entity.Parameters;
import by.bsu.xml.entity.Tariff;
import by.bsu.xml.entity.Tariffs;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 * Created by Виктория on 05.01.2016.
 */
public class TariffMarshal {
    private static Logger logger = Logger.getLogger(TariffMarshal.class);
    public static void marshalTariff() {
        try {
            JAXBContext context = JAXBContext.newInstance(Tariffs.class);
            Marshaller m = context.createMarshaller();
            m.marshal(createTariffs(), new FileOutputStream("data/tariffs_marsh.xml"));
            logger.info("XML-file has created");
        } catch (FileNotFoundException e) {
            logger.info("XML-файл can't be created: " + e);
        } catch (JAXBException e) {
            logger.info("JAXB-context has an error " + e);
        }
    }

    private static Tariffs createTariffs() {
        Tariffs tariffs = new Tariffs();
        CallPrices callPrices = new CallPrices();
        callPrices.setInNet(200);
        callPrices.setOutNet(500);
        callPrices.setLandline(1000);
        Parameters parameters = new Parameters();
        parameters.setPayment(new BigInteger("20000"));
        parameters.setTarification("minute");
        parameters.setFavourites(5);
        Tariff tariff = new Tariff();
        tariff.setOperatorName("Velcom");
        tariff.setName("SuperTariff");
        tariff.setPayroll(25000);
        tariff.setParameters(parameters);
        tariff.setCallPrices(callPrices);
        tariff.setSms(100);
        Tariff tariff2 = new Tariff();
        tariff2.setOperatorName("MTS");
        tariff2.setName("TalkForever");
        tariff2.setPayroll(15000);
        tariff2.setParameters(parameters);
        tariff2.setCallPrices(callPrices);
        tariff2.setSms(180);
        tariffs.add(tariff);
        tariffs.add(tariff2);
        return tariffs;
    }
}
