package by.bsu.xml.action;

import by.bsu.xml.entity.Tariff;
import by.bsu.xml.entity.TariffEnum;
import org.apache.commons.lang3.EnumUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 03.01.2016.
 */
public class TariffHandler extends DefaultHandler {
    private final String DEFAULT_OPERATOR = "Velcom";
    private List<Tariff> tariffs;
    private Tariff tariff = null;
    private String current = null;


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
                tariff.setOperatorName(DEFAULT_OPERATOR);
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
            if (EnumUtils.isValidEnum(TariffEnum.class, current.toUpperCase())) {
                switch (current) {
                    case "payroll":
                        tariff.setPayroll(Integer.parseInt(s));
                        break;
                    case "innet":
                        tariff.getCallPrices().setInNet(Integer.parseInt(s));
                        break;
                    case "outnet":
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
        }
        current = null;
    }
}
