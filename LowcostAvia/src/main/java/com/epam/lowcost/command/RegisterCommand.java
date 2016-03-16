package com.epam.lowcost.command;

import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Виктория on 24.02.2016.
 */
public class RegisterCommand extends ActionCommand {
    private static final String PARAM_NAME_USERNAME = "user";
    private static final String PARAM_NAME_PASSWORD = "pass";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_NUMBER = "number";
    private static final String PARAM_NAME_CARD = "card";
    private static final String PARAM_NAME_AMOUNT = "amount";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        String page;
        String username = request.getParameter(PARAM_NAME_USERNAME);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String number = request.getParameter(PARAM_NAME_NUMBER);
        String cardType = request.getParameter(PARAM_NAME_CARD);
        String amount = request.getParameter(PARAM_NAME_AMOUNT);
        UserService userService = UserService.getInstance();
        if (Validator.validateRegistrationForm(username, password, name, surname, email, number, cardType, amount) && !userService.persist(username)) {
            userService.createUser(username, password, name, surname, email, number, cardType, amount);
            page = "/index.jsp";
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        }

    }
}
