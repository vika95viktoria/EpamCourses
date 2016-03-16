package com.epam.lowcost.command;

import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.security.PasswordHash;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Виктория on 06.03.2016.
 */
public class DeleteFlightsCommand extends ActionCommand {
    private static final String PARAM_NAME_DELETED = "toBeDeleted";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        String[] ids = request.getParameterValues(PARAM_NAME_DELETED);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        if (Validator.validateDeleteFlightsForm(password, ids)) {
            HttpSession session = request.getSession();
            String username = session.getAttribute("currentUser").toString();
            UserService userService = UserService.getInstance();
            User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
            ServiceMessage message;
            if (user != null) {
                FlightService flightService = FlightService.getInstance();
                flightService.deleteFlights(ids);
                message = ServiceMessage.OKEDIT;
            } else {
                message = ServiceMessage.ERRORPASSWORD;
            }
            response.getWriter().print(message.getValue());


        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
