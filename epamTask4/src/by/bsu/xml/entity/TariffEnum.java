package by.bsu.xml.entity;

/**
 * Created by Виктория on 03.01.2016.
 */
public enum  TariffEnum {
    TARIFFS("tariffs"),
    NAME("name"),
    OPERATOR_NAME("operatorName"),
/*    CALLPRICES("callPrices"),*/
/*    TARIFF("tariff"),*/
    PAYROLL("payroll"),
    INNET("inNet"),
    OUTNET("outNet"),
    LANDLINE("landline"),
    SMS("sms"),/*
    PARAMETERS("parameters"),*/
    FAVOURITES("favourites"),
    TARIFICATION("tarification"),
    PAYMENT("payment");

    private String value;
    private TariffEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
