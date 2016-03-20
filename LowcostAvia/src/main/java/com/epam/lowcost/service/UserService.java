package com.epam.lowcost.service;

import com.epam.lowcost.dao.CreditCardDAO;
import com.epam.lowcost.dao.UserDAO;
import com.epam.lowcost.domain.CreditCard;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.security.PasswordHash;

/**
 * Created by Виктория on 25.02.2016.
 */
public class UserService {
    private static UserService userService = new UserService();
    UserDAO userDAO = UserDAO.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return userService;
    }

    public User findByUsernamePassword(String username, String password) throws ServiceException {
        User user;
        try {
            user = userDAO.findByUsernamePassword(username, password);
            if (user.getId() == 0) {
                return null;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public User findUserById(Long id) throws ServiceException {
        User user;
        try {
            user = userDAO.findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public boolean persist(String username) throws ServiceException {
        try {
            if (userDAO.persist(username) == -1) {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    public ServiceMessage createUser(String username, String password, String name, String surname, String email, String number, String cardType, String amount) throws ServiceException {
        try {
            if (userDAO.persist(username) == -1) {
                User user = new User();
                CreditCard creditCard = new CreditCard();
                creditCard.setAmount(Double.parseDouble(amount));
                creditCard.setId(Long.parseLong(number));
                creditCard.setType(cardType);
                CreditCardDAO cardDAO = CreditCardDAO.getInstance();
                cardDAO.create(creditCard);
                user.setName(name);
                user.setUsername(username);
                user.setSurname(surname);
                user.setPassword(PasswordHash.getSecurePassword(password));
                user.setEmail(email);
                user.setCard(creditCard);
                userDAO.create(user);
                return ServiceMessage.OK;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return ServiceMessage.USERNAME_PERSIST;
    }
}
