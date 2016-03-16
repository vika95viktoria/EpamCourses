package com.epam.lowcost.command;

import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.RoutesService;
import com.epam.lowcost.util.DateUtils;
import com.epam.lowcost.util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Виктория on 05.03.2016.
 */
public class AddRouteCommand extends ActionCommand {
    private static final String PARAM_NAME_FROM = "from";
    private static final String PARAM_NAME_TO = "to";
    private static final String PARAM_NAME_ECONOMY_COUNT = "economyCount";
    private static final String PARAM_NAME_ECONOMY_PRICE = "economyPrice";
    private static final String PARAM_NAME_BUSINESS_COUNT = "businessCount";
    private static final String PARAM_NAME_BUSINESS_PRICE = "businessPrice";
    private static final String PARAM_NAME_TIME_FROM = "timeFrom";
    private static final String PARAM_NAME_TIME_TO = "timeTo";
    private static final String PARAM_NAME_WEEK = "week";
    private static final String PARAM_NAME_DATE_FROM = "dateFrom";
    private static final String PARAM_NAME_DATE_TO = "dateTo";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, ServletException, IOException {
        String from = request.getParameter(PARAM_NAME_FROM);
        String to = request.getParameter(PARAM_NAME_TO);
        String economyCount = request.getParameter(PARAM_NAME_ECONOMY_COUNT);
        String businessCount = request.getParameter(PARAM_NAME_BUSINESS_COUNT);
        String economyPrice = request.getParameter(PARAM_NAME_ECONOMY_PRICE);
        String businessPrice = request.getParameter(PARAM_NAME_BUSINESS_PRICE);
        String timeFrom = request.getParameter(PARAM_NAME_TIME_FROM);
        String timeTo = request.getParameter(PARAM_NAME_TIME_TO);
        String[] days = request.getParameterValues(PARAM_NAME_WEEK);
        String dateFrom = request.getParameter(PARAM_NAME_DATE_FROM);
        String dateTo = request.getParameter(PARAM_NAME_DATE_TO);
        if (Validator.validateAddRouteForm(from, to, economyCount, businessCount, economyPrice, businessPrice, timeFrom, timeTo, dateFrom, dateTo, days)) {
            Date startDate = DateUtils.parseDate(dateFrom, timeFrom);
            Date startDateArrival = DateUtils.parseDate(dateFrom, timeTo);
            startDateArrival = DateUtils.checkDates(startDate, startDateArrival);
            Date endDate = DateUtils.parseDate(dateTo, timeTo);
            RoutesService routesService = RoutesService.getInstance();
            FlightService flightService = FlightService.getInstance();
            Flight flight = new Flight();
            Route route = new Route();

            Long routeId = routesService.createRoute(from, to);
            route.setId(routeId);
            flight.setRoute(route);
            flight.setDateOut(startDate);
            flight.setDateIn(startDateArrival);
            flight.setEconomyCount(Integer.valueOf(economyCount));
            flight.setBusinessCount(Integer.valueOf(businessCount));
            flight.setEconomyPrice(Double.valueOf(economyPrice));
            flight.setBusinessPrice(Double.valueOf(businessPrice));
            flightService.generateFlights(flight, startDate, startDateArrival, endDate, days);

            String page = "/jsp/createRoute.jsp";
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

        }

    }
}
