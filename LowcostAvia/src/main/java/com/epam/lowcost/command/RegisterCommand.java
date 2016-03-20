package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.resource.ConfigurationManager;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 24.02.2016.
 */
public class RegisterCommand extends ActionCommand {
    /**
     * Get parameters, check if valid, check if username is unique, add user to database, return to main page
     *
     * @param request
     * @param response
     * @throws ServiceException
     * @throws ServletException
     * @throws IOException
     * @throws ValidationException
     */

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException, ValidationException {
        String username = request.getParameter(PARAM_NAME_USERNAME_REGISTER);
        String password = request.getParameter(PARAM_NAME_PASSWORD_REGISTER);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String number = request.getParameter(PARAM_NAME_NUMBER);
        String cardType = request.getParameter(PARAM_NAME_CARD);
        String amount = request.getParameter(PARAM_NAME_AMOUNT);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        UserService userService = UserService.getInstance();
        ServiceMessage message = Validator.validateRegistrationForm(username, password, name, surname, email, number, cardType, amount);
        if (!ServiceMessage.OK.equals(message)) {
            request.setAttribute(ERROR_MESSAGE, message.getValue(language));
            throw new ValidationException();
        }
        if (!userService.persist(username)) {
            request.setAttribute(ERROR_MESSAGE, ServiceMessage.USERNAME_PERSIST.getValue(language));
            throw new ValidationException();
        }
        userService.createUser(username, password, name, surname, email, number, cardType, amount);
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(INDEX_PATH);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);


    }
}
