package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.security.PasswordHash;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.Validator;
import static com.epam.lowcost.util.CommandConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Виктория on 06.03.2016.
 */
public class DeleteFlightsCommand extends ActionCommand {

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ValidationException {
        String[] ids = request.getParameterValues(PARAM_NAME_DELETED);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        HttpSession session = request.getSession();
        String language = (String)session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        ServiceMessage message = Validator.validateDeleteFlightsForm(password, ids);
        if (!ServiceMessage.OK.equals(message)) {
            request.setAttribute(ERROR_MESSAGE,message.getValue(language));
            throw new ValidationException();
        }
            String username = session.getAttribute(ATTRIBUTE_NAME_USER).toString();
            UserService userService = UserService.getInstance();
            User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
            if (user != null) {
                FlightService flightService = FlightService.getInstance();
                flightService.deleteFlights(ids);
                message = ServiceMessage.OK_EDIT;
            } else {
                request.setAttribute(ATTRIBUTE_NAME_ERROR,true);
                message = ServiceMessage.ERROR_PASSWORD;
            }
            response.getWriter().print(message.getValue(language));

    }
}
