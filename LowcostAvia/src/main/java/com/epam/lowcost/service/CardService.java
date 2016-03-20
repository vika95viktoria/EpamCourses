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
    private static final String PRICE_PROPERTIES_FILE = "price.properties";
    private static final String LUGGAGE = "luggage";
    private static final String PRIORITY_PRICE = "priorityPrice";
    private static CardService cardService = new CardService();
    private CreditCardDAO cardDAO = CreditCardDAO.getInstance();

    private CardService() {
    }

    public static CardService getInstance() {
        return cardService;
    }

    /**
     * Update ticket info with new luggage and priority parameters
     *
     * @param priority
     * @param luggage
     * @param userId
     * @param luggageChange
     * @param priorChange
     * @return
     * @throws ServiceException
     */
    public ServiceMessage editTicket(int priority, int luggage, Long userId, boolean luggageChange, boolean priorChange) throws ServiceException {
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties(PRICE_PROPERTIES_FILE);
        ServiceMessage message;
        double price = 0;
        if (luggage != 0 && luggageChange) {
            String luggageKey = LUGGAGE + luggage;
            price += Double.parseDouble(priceManager.getProperty(luggageKey));
        }
        if (priority != 0 && priorChange) {
            price += Double.parseDouble(priceManager.getProperty(PRIORITY_PRICE));
        }
        try {
            message = cardDAO.updateAmount(price, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return message;
    }
}
