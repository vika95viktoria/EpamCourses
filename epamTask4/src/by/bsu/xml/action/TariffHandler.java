package by.bsu.xml.action;

import by.bsu.xml.entity.Tariff;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 03.01.2016.
 */
public class TariffHandler extends DefaultHandler {
    private List<Tariff> tariffs;
    private Tariff tariff = null;
    private String current = null;
    private final String defaultOperatorName = "Velcom";

    public TariffHandler() {
        tariffs = new ArrayList<>();
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if ("tariff".equalsIgnoreCase(localName)) {
            tariff = new Tariff();
            tariff.setName(attrs.getValue(0));
            if (attrs.getLength() == 2) {
                tariff.setOperatorName(attrs.getValue(1));
            } else {
                tariff.setOperatorName(defaultOperatorName);
            }
        } else {
            current = localName;
        }

    }

    public void endElement(String uri, String localName, String qName) {
        if ("tariff".equals(localName)) {
            tariffs.add(tariff);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (current != null) {
            switch (current) {
                case "payroll":
                    tariff.setPayroll(Integer.parseInt(s));
                    break;
                case "inNet":
                    tariff.getCallPrices().setInNet(Integer.parseInt(s));
                    break;
                case "outNet":
                    tariff.getCallPrices().setOutNet(Integer.parseInt(s));
                    break;
                case "landline":
                    tariff.getCallPrices().setLandline(Integer.parseInt(s));
                    break;
                case "sms":
                    tariff.setSms(Integer.parseInt(s));
                    break;
                case "favourites":
                    tariff.getParameters().setFavourites(Integer.parseInt(s));
                    break;
                case "tarification":
                    tariff.getParameters().setTarification(s);
                    break;
                case "payment":
                    tariff.getParameters().setPayment(new BigInteger(s));
                    break;
                default:
                    break;

            }
        }
        current = null;
    }
}
