package com.epam.lowcost.command;


import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.resource.ConfigurationManager;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.RoutesService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Виктория on 20.02.2016.
 */
public class FlightsCommand extends ActionCommand {
    private static final String PARAM_NAME_FROM = "selectFrom";
    private static final String PARAM_NAME_TO = "selectTo";
    private static final String PARAM_NAME_DATEOUT = "dateOut";
    private static final String PARAM_NAME_DATEIN = "dateIn";
    private static final String PARAM_NAME_COUNT = "count";
    private static final String PARAM_NAME_RETURN = "return";

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, ParseException {
        String page;
        if (request.getParameter("routeId") != null) {
            findByRoute(request);
        } else {
            search(request);
        }
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties("price.properties");
        request.setAttribute("luggage15", priceManager.getProperty("luggage15"));
        request.setAttribute("luggage20", priceManager.getProperty("luggage20"));
        request.setAttribute("luggage25", priceManager.getProperty("luggage25"));
        request.setAttribute("priorityPrice", priceManager.getProperty("priority"));
        page = "/jsp/flightPage.jsp";
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }

    private void findByRoute(HttpServletRequest request) throws ServiceException {
        Long id = Long.valueOf(request.getParameter("routeId"));
        FlightService flightService = FlightService.getInstance();
        RoutesService routesService = RoutesService.getInstance();
        Route route = routesService.getRouteById(id);
        List<Flight> flights = flightService.getAllRoutes(id);
        request.setAttribute("cityFrom", route.getCityFrom());
        request.setAttribute("cityTo", route.getCityTo());
        request.setAttribute("flights", flights);
        request.setAttribute("count", 1);
        if (flights.isEmpty()) {
            request.setAttribute("errorFlight", "No flights find for this date");
        }

    }

    private void search(HttpServletRequest request) throws ParseException, ServiceException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String from = request.getParameter(PARAM_NAME_FROM);
        String to = request.getParameter(PARAM_NAME_TO);

        Date dateOut = new Date(formatter.parse(request.getParameter(PARAM_NAME_DATEOUT)).getTime());
        Date dateIn = new Date(formatter.parse(request.getParameter(PARAM_NAME_DATEIN)).getTime());

        String count = request.getParameter(PARAM_NAME_COUNT);
        Boolean isReturn = Boolean.valueOf(request.getParameter(PARAM_NAME_RETURN));
        FlightService flightService = FlightService.getInstance();
        List<Flight> returnFlights;

        if (isReturn) {
            returnFlights = flightService.getByCityName(to, from, dateIn);
            if (returnFlights.isEmpty()) {
                request.setAttribute("errorReturn", "No flights find for this date");
            }
            request.setAttribute("returnFlights", returnFlights);
            request.setAttribute("isReturn", true);
        }
        String countOfTickets = count.substring(0, 1);
        List<Flight> flights = flightService.getByCityName(from, to, dateOut);
        request.setAttribute("cityFrom", from);
        request.setAttribute("cityTo", to);
        request.setAttribute("flights", flights);
        request.setAttribute("count", Integer.valueOf(countOfTickets));
        if (flights.isEmpty()) {
            request.setAttribute("errorFlight", "No flights find for this date");
        }

    }
}
