package com.epam.lowcost.domain;

import com.epam.lowcost.resource.ConfigurationManager;

/**
 * Created by Виктория on 01.03.2016.
 */
public enum ServiceMessage {
    NO_TICKET,
    CITY_OK,
    ERROR_PASSWORD,
    USERNAME_PERSIST,
    CITY_FAIL,
    NO_MONEY,
    OK_EDIT,
    OK_BUY,
    OK,
    CITY_INPUT_FAIL,
    COUNT_FAIL,
    PRICE_FAIL,
    DATE_FAIL,
    TIME_FAIL,
    DAY_FAIL,
    ID_FAIL,
    PASSWORD_FAIL,
    LOGIN_FAIL,
    USERNAME_FAIL,
    NAME_FAIL,
    SURNAME_FAIL,
    EMAIL_FAIL,
    CARD_NUMBER_FAIL,
    CARD_TYPE_FAIL,
    AMOUNT_FAIL;

    public String getValue(String language) {
        ConfigurationManager manager = new ConfigurationManager();
        String filename = "pagecontent_"+language+".properties";
        manager.loadProperties(filename);
        return manager.getProperty(this.toString());
    }
}
