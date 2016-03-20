package com.epam.lowcost.command;

import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.security.PasswordHash;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.UserService;
import com.epam.lowcost.util.DateUtils;
import com.epam.lowcost.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 06.03.2016.
 */
public class EditFlightCommand extends ActionCommand {
    /**
     * Check if password is correct and edit flight conditions
     *
     * @param request
     * @param response
     * @throws ServiceException
     * @throws IOException
     * @throws ValidationException
     */

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ValidationException {
        String economyPrice = request.getParameter(PARAM_NAME_EDIT_ECONOMY_PRICE);
        String businessPrice = request.getParameter(PARAM_NAME_EDIT_BUSINESS_PRICE);
        String timeOut = request.getParameter(PARAM_NAME_TIME_OUT);
        String timeIn = request.getParameter(PARAM_NAME_TIME_IN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String flightId = request.getParameter(PARAM_NAME_FLIGHT_ID);
        String dateOut = request.getParameter(PARAM_NAME_DATE_OUT);
        String dateIn = request.getParameter(PARAM_NAME_DATE_IN);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        ServiceMessage message = Validator.validateEditFlightForm(economyPrice, businessPrice, timeOut, timeIn, password);
        if (!ServiceMessage.OK.equals(message)) {
            request.setAttribute(ERROR_MESSAGE, message.getValue(language));
            throw new ValidationException();
        }
        Date departureDate = new Date(Long.valueOf(dateOut));
        Date arrivalDate = new Date(Long.valueOf(dateIn));
        String username = session.getAttribute(ATTRIBUTE_NAME_USER).toString();
        UserService userService = UserService.getInstance();
        User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
        if (user != null) {
            Flight flight = new Flight();
            flight.setBusinessPrice(Double.valueOf(businessPrice));
            flight.setEconomyPrice(Double.valueOf(economyPrice));
            flight.setId(Long.valueOf(flightId));
            departureDate = DateUtils.parseDate(DateUtils.getDateWithoutTime(departureDate), timeOut);
            arrivalDate = DateUtils.parseDate(DateUtils.getDateWithoutTime(arrivalDate), timeIn);
            arrivalDate = DateUtils.checkDates(departureDate, arrivalDate);
            flight.setDateOut(departureDate);
            flight.setDateIn(arrivalDate);
            FlightService flightService = FlightService.getInstance();
            flightService.update(flight);
            message = ServiceMessage.OK_EDIT;
        } else {
            request.setAttribute(ATTRIBUTE_NAME_ERROR, true);
            message = ServiceMessage.ERROR_PASSWORD;
        }
        response.getWriter().print(message.getValue(language));

    }
}

