package com.epam.lowcost.util;

import com.epam.lowcost.domain.ServiceMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Виктория on 06.03.2016.
 */
public class Validator {
    private static final String CITY_REGEX = "^(([A-Z][a-z]*)|([А-Я][а-я]*))(?:[\\s-](([a-zA-Z])|([а-яА-Я]))+)*";
    private static final String PRICE_REGEX = "^[1-9][0-9]*";
    private static final String TIME_REGEX = "((^[1-9])|(^[1][0-2])):[0-5][0-9] ((AM)|(PM))";
    private static final String PASSWORD_USERNAME_REGEX = "^[a-zA-Zа-яА-Я\\w]{3,20}$";
    private static final String NAME_SURNAME_REGEX = "^(([A-Z][a-z]*)|([А-Я][а-я]*))([-](([a-zA-Z])|([а-яА-Я]))+)?";
    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final String CARD_TYPE_REGEX = "^(([A-Z][a-z]*\\s?)*)";
    private static final String CARD_NUMBER_REGEX = "^[0-9]{16}";
    private static final String AMOUNT_REGEX = "^([1-9][0-9]{1,3})|([1][0]{4})";
    private static final String ID_REGEX = "[1-9][0-9]*";
    private static final String DATE_REGEX = "^(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])-(?:[0-9]{2})?[0-9]{2}$";
    private static final String DAY_REGEX = "[0-6]";

    public static ServiceMessage validateRegistrationForm(String username, String password, String name, String surname, String email, String number, String cardType, String amount) {
        if (!check(PASSWORD_USERNAME_REGEX, username)) {
            return ServiceMessage.USERNAME_FAIL;
        }
        if (!check(PASSWORD_USERNAME_REGEX, password)) {
            return ServiceMessage.PASSWORD_FAIL;
        }
        if (!check(NAME_SURNAME_REGEX, name)) {
            return ServiceMessage.NAME_FAIL;
        }
        if (!check(NAME_SURNAME_REGEX, surname)) {
            return ServiceMessage.SURNAME_FAIL;
        }
        if (!check(EMAIL_REGEX, email)) {
            return ServiceMessage.EMAIL_FAIL;
        }
        if (!check(CARD_NUMBER_REGEX, number)) {
            return ServiceMessage.CARD_NUMBER_FAIL;
        }
        if (!check(CARD_TYPE_REGEX, cardType)) {
            return ServiceMessage.CARD_TYPE_FAIL;
        }
        if (!check(AMOUNT_REGEX, amount)) {
            return ServiceMessage.AMOUNT_FAIL;
        }
        return ServiceMessage.OK;
    }

    public static boolean validateLoginForm(String username, String password) {
        if (!check(PASSWORD_USERNAME_REGEX, username)) {
            return false;
        }
        if (!check(PASSWORD_USERNAME_REGEX, password)) {
            return false;
        }
        return true;
    }

    public static ServiceMessage validateAddRouteForm(String from, String to, String economyCount, String businessCount, String economyPrice, String businessPrice, String timeFrom, String timeTo, String dateFrom, String dateTo, String[] days) {
        if (!check(CITY_REGEX, from)) {
            return ServiceMessage.CITY_INPUT_FAIL;
        }
        if (!check(CITY_REGEX, to)) {
            return ServiceMessage.CITY_INPUT_FAIL;
        }
        if (!check(PRICE_REGEX, economyCount)) {
            return ServiceMessage.COUNT_FAIL;
        }
        if (!check(PRICE_REGEX, businessCount)) {
            return ServiceMessage.COUNT_FAIL;
        }
        if (!check(PRICE_REGEX, economyPrice)) {
            return ServiceMessage.PRICE_FAIL;
        }
        if (!check(PRICE_REGEX, businessPrice)) {
            return ServiceMessage.PRICE_FAIL;
        }
        if (!check(TIME_REGEX, timeFrom)) {
            return ServiceMessage.TIME_FAIL;
        }
        if (!check(TIME_REGEX, timeTo)) {
            return ServiceMessage.TIME_FAIL;
        }
        if (!check(DATE_REGEX, dateFrom)) {
            return ServiceMessage.DATE_FAIL;
        }
        if (!check(DATE_REGEX, dateTo)) {
            return ServiceMessage.DATE_FAIL;
        }
        for (String day : days) {
            if (!check(DAY_REGEX, day)) {
                return ServiceMessage.DAY_FAIL;
            }
        }
        return ServiceMessage.OK;
    }

    public static boolean validateAddCityForm(String city) {
        return check(CITY_REGEX, city);
    }

    public static ServiceMessage validateDeleteFlightsForm(String password, String[] ids) {
        if (!check(PASSWORD_USERNAME_REGEX, password)) {
            return ServiceMessage.PASSWORD_FAIL;
        }
        for (String id : ids) {
            if (!check(ID_REGEX, id)) {
                return ServiceMessage.ID_FAIL;
            }
        }
        return ServiceMessage.OK;
    }

    public static ServiceMessage validateEditFlightForm(String economyPrice, String businessPrice, String timeFrom, String timeTo, String password) {

        if (!check(PRICE_REGEX, economyPrice)) {
            return ServiceMessage.PRICE_FAIL;
        }
        if (!check(PRICE_REGEX, businessPrice)) {
            return ServiceMessage.PRICE_FAIL;
        }
        if (!check(TIME_REGEX, timeFrom)) {
            return ServiceMessage.TIME_FAIL;
        }
        if (!check(TIME_REGEX, timeTo)) {
            return ServiceMessage.TIME_FAIL;
        }
        if (!check(PASSWORD_USERNAME_REGEX, password)) {
            return ServiceMessage.PASSWORD_FAIL;
        }
        return ServiceMessage.OK;
    }

    private static boolean check(String regex, String forCheck) {
        if (forCheck != null && !forCheck.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(forCheck);
            return matcher.matches();
        }
        return false;
    }
}
