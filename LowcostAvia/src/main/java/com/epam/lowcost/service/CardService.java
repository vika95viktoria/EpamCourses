package com.epam.lowcost.service;

import com.epam.lowcost.dao.CreditCardDAO;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.resource.ConfigurationManager;

/**
 * Created by Виктория on 03.03.2016.
 */
public class CardService {
    private static CardService cardService = new CardService();
    private CreditCardDAO cardDAO = CreditCardDAO.getInstance();

    private CardService() {
    }

    public static CardService getInstance() {
        return cardService;
    }

    public ServiceMessage editTicket(int priority, int luggage, Long userId, boolean luggageChange, boolean priorChange) throws ServiceException {
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties("price.properties");
        ServiceMessage message;
        double price = 0;
        if (luggage != 0 && luggageChange) {
            String luggageKey = "luggage" + luggage;
            price += Double.parseDouble(priceManager.getProperty(luggageKey));
        }
        if (priority != 0 && priorChange) {
            price += Double.parseDouble(priceManager.getProperty("priority"));
        }
        try {
            message = cardDAO.updateAmount(price, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return message;
    }
}
