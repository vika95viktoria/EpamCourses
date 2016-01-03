package by.bsu.xml.builder;

import by.bsu.xml.entity.CallPrices;
import by.bsu.xml.entity.Parameters;
import by.bsu.xml.entity.Tariff;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 04.01.2016.
 */
public class TariffsStAXBuilder {
    private static Logger logger = Logger.getLogger(TariffsStAXBuilder.class);
    private ArrayList<Tariff> tariffs = new ArrayList<>();
    private XMLInputFactory inputFactory;

    public TariffsStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void buildTariffs(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if ("tariff".equalsIgnoreCase(name)) {
                        Tariff tariff = buildTariff(reader);
                        tariffs.add(tariff);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            logger.error("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            logger.error("File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("Impossible close file " + fileName + " : " + e);
            }
        }
    }

    private Tariff buildTariff(XMLStreamReader reader) throws XMLStreamException {
        Tariff tariff = new Tariff();
        tariff.setName(reader.getAttributeValue(null, "name"));
        if (reader.getAttributeValue(null, "operatorName") != null) {
            tariff.setOperatorName(reader.getAttributeValue(null, "operatorName"));
        } else {
            tariff.setOperatorName("Velcom");
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (name) {
                        case "payroll":
                            tariff.setPayroll(Integer.parseInt(getXMLText(reader)));
                            break;
                        case "callPrices":
                            tariff.setCallPrices(getXMLCallPrices(reader));
                            break;
                        case "sms":
                            tariff.setSms(Integer.parseInt(getXMLText(reader)));
                            break;
                        case "parameters":
                            tariff.setParameters(getXMLParameters(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if ("tariff".equalsIgnoreCase(name)) {
                        return tariff;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Tariff");
    }

    private CallPrices getXMLCallPrices(XMLStreamReader reader) throws XMLStreamException {
        CallPrices callPrices = new CallPrices();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (name) {
                        case "inNet":
                            callPrices.setInNet(Integer.parseInt(getXMLText(reader)));
                            break;
                        case "outNet":
                            callPrices.setOutNet(Integer.parseInt(getXMLText(reader)));
                            break;
                        case "landline":
                            callPrices.setLandline(Integer.parseInt(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if ("callPrices".equalsIgnoreCase(name)) {
                        return callPrices;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag CallPrices");
    }

    private Parameters getXMLParameters(XMLStreamReader reader) throws XMLStreamException {
        Parameters parameters = new Parameters();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (name) {
                        case "favourites":
                            parameters.setFavourites(Integer.parseInt(getXMLText(reader)));
                            break;
                        case "tarification":
                            parameters.setTarification(getXMLText(reader));
                            break;
                        case "payment":
                            parameters.setPayment(new BigInteger(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if ("parameters".equalsIgnoreCase(name)) {
                        return parameters;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Parameters");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
