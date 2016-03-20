package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.resource.ConfigurationManager;
import com.epam.lowcost.security.PasswordHash;
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
 * Created by Виктория on 18.02.2016.
 */
public class LoginCommand extends ActionCommand {
    /**
     * Get password and username, check if valid, check if user with such credentials exists
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
        String username = request.getParameter(PARAM_NAME_USERNAME);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        UserService userService = UserService.getInstance();
        if (!Validator.validateLoginForm(username, password)) {
            request.setAttribute(ERROR_MESSAGE, ServiceMessage.LOGIN_FAIL.getValue(language));
            throw new ValidationException();
        }
        User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
        if (user != null) {
            session.setAttribute(ATTRIBUTE_NAME_USER, user.getUsername());
            session.setAttribute(ATTRIBUTE_NAME_USER_ID, user.getId());
            if (user.isAdmin()) {
                session.setAttribute(ATTRIBUTE_NAME_ROLE, ADMIN_ROLE);
            } else {
                session.setAttribute(ATTRIBUTE_NAME_ROLE, USER_ROLE);
            }
        } else {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN_PASS, true);
        }
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(INDEX_PATH);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}

