package com.epam.lowcost.command;

import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.ServiceException;
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

/**
 * Created by Виктория on 06.03.2016.
 */
public class EditFlightCommand extends ActionCommand {
    private static final String PARAM_NAME_ECONOMY_PRICE = "EditEconomyPrice";
    private static final String PARAM_NAME_BUSINESS_PRICE = "EditBusinessPrice";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_TIME_OUT = "newTimeOut";
    private static final String PARAM_NAME_TIME_IN = "newTimeIn";
    private static final String PARAM_NAME_ID = "flightId";
    private static final String PARAM_NAME_DATE_OUT = "newDateOut";
    private static final String PARAM_NAME_DATE_IN = "newDateIn";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        String economyPrice = request.getParameter(PARAM_NAME_ECONOMY_PRICE);
        String businessPrice = request.getParameter(PARAM_NAME_BUSINESS_PRICE);
        String timeOut = request.getParameter(PARAM_NAME_TIME_OUT);
        String timeIn = request.getParameter(PARAM_NAME_TIME_IN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String flightId = request.getParameter(PARAM_NAME_ID);
        String dateOut = request.getParameter(PARAM_NAME_DATE_OUT);
        String dateIn = request.getParameter(PARAM_NAME_DATE_IN);
        if (Validator.validateEditFlightForm(economyPrice, businessPrice, timeOut, timeIn, password)) {
            Date departureDate = new Date(Long.valueOf(dateOut));
            Date arrivalDate = new Date(Long.valueOf(dateIn));
            HttpSession session = request.getSession();
            String username = session.getAttribute("currentUser").toString();
            UserService userService = UserService.getInstance();
            User user = userService.findByUsernamePassword(username, PasswordHash.getSecurePassword(password));
            ServiceMessage message;
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

