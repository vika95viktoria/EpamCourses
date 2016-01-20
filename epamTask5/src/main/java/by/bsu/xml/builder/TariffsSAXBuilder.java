package by.bsu.xml.builder;

import by.bsu.xml.action.TariffHandler;
import by.bsu.xml.entity.Tariff;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Виктория on 03.01.2016.
 */
public class TariffsSAXBuilder implements AbstractTariffsBuilder {
    private static Logger logger = Logger.getLogger(TariffsSAXBuilder.class);
    private List<Tariff> tariffs;
    private TariffHandler tariffHandler;
    private XMLReader reader;

    public TariffsSAXBuilder() {
        tariffHandler = new TariffHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(tariffHandler);
        } catch (SAXException e) {
            logger.error("SAX parser's error: " + e);
        }
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void buildTariffs(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            logger.error("SAX parser's error: " + e);
        } catch (IOException e) {
            logger.error("I/O stream's error: " + e);
        }
        tariffs = tariffHandler.getTariffs();
    }
}
