package by.bsu.xml.builder;

import by.bsu.xml.entity.CallPrices;
import by.bsu.xml.entity.Parameters;
import by.bsu.xml.entity.Tariff;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 03.01.2016.
 */
public class TariffsDOMBuilder {
    private static Logger logger = Logger.getLogger(TariffsDOMBuilder.class);
    private List<Tariff> tariffs;
    private DocumentBuilder docBuilder;

    public TariffsDOMBuilder() {
        this.tariffs = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера: " + e);
        }
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void buildTariffs(String fileName) {
        Document doc = null;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList tariffsList = root.getElementsByTagName("tariff");
            for (int i = 0; i < tariffsList.getLength(); i++) {
                Element tariffElement = (Element) tariffsList.item(i);
                Tariff tariff = buildTariff(tariffElement);
                tariffs.add(tariff);
            }
        } catch (IOException e) {
            logger.error("File error or I/O error: " + e);
        } catch (SAXException e) {
            logger.error("Parsing failure: " + e);
        }
    }

    private Tariff buildTariff(Element tariffElement) {
        Tariff tariff = new Tariff();
        tariff.setName(tariffElement.getAttribute("name"));
        if (tariffElement.getAttribute("operatorName") != null) {
            tariff.setOperatorName(tariffElement.getAttribute("operatorName"));
        } else {
            tariff.setOperatorName("Velcom");
        }
        Integer payroll = Integer.parseInt(getElementTextContent(tariffElement, "payroll"));
        tariff.setPayroll(payroll);
        modifyCallPrices(tariff, tariffElement);
        Integer sms = Integer.parseInt(getElementTextContent(tariffElement, "sms"));
        tariff.setSms(sms);
        modifyParameters(tariff, tariffElement);
        return tariff;
    }

    private void modifyParameters(Tariff tariff, Element tariffElement) {
        Parameters parameters = tariff.getParameters();
        Element parametersElement = (Element) tariffElement.getElementsByTagName("parameters").item(0);
        Integer favourites = Integer.parseInt(getElementTextContent(parametersElement, "favourites"));
        parameters.setFavourites(favourites);
        parameters.setTarification(getElementTextContent(parametersElement, "tarification"));
        BigInteger payment = new BigInteger(getElementTextContent(parametersElement, "payment"));
        parameters.setPayment(payment);
    }

    private void modifyCallPrices(Tariff tariff, Element tariffElement) {
        CallPrices callPrices = tariff.getCallPrices();
        Element callPricesElement = (Element) tariffElement.getElementsByTagName("callPrices").item(0);
        Integer inNet = Integer.parseInt(getElementTextContent(callPricesElement, "inNet"));
        callPrices.setInNet(inNet);
        Integer outNet = Integer.parseInt(getElementTextContent(callPricesElement, "outNet"));
        callPrices.setOutNet(outNet);
        Integer landline = Integer.parseInt(getElementTextContent(callPricesElement, "landline"));
        callPrices.setLandline(landline);
    }
}
