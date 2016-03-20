package com.epam.lowcost.command;


import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.resource.ConfigurationManager;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.RoutesService;
import static com.epam.lowcost.util.CommandConstants.*;

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


    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, ParseException {
        if (request.getParameter(PARAM_NAME_ROUTE_ID) != null) {
            findByRoute(request);
        } else {
            search(request);
        }
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties(PRICE_PROPERTIES_FILE);
        request.setAttribute(LUGGAGE_15, priceManager.getProperty(LUGGAGE_15));
        request.setAttribute(LUGGAGE_20, priceManager.getProperty(LUGGAGE_20));
        request.setAttribute(LUGGAGE_25, priceManager.getProperty(LUGGAGE_25));
        request.setAttribute(PRIORITY_PRICE, priceManager.getProperty(PRIORITY_PRICE));
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page = configurationManager.getProperty(FLIGHT_PAGE_PATH);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }

    private void findByRoute(HttpServletRequest request) throws ServiceException {
        Long id = Long.valueOf(request.getParameter(PARAM_NAME_ROUTE_ID));
        FlightService flightService = FlightService.getInstance();
        RoutesService routesService = RoutesService.getInstance();
        Route route = routesService.getRouteById(id);
        List<Flight> flights = flightService.getAllRoutes(id);
        request.setAttribute(ATTRIBUTE_NAME_CITY_FROM, route.getCityFrom());
        request.setAttribute(ATTRIBUTE_NAME_CITY_TO, route.getCityTo());
        request.setAttribute(ATTRIBUTE_NAME_FLIGHTS, flights);
        request.setAttribute(ATTRIBUTE_NAME_COUNT, 1);
        if (flights.isEmpty()) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_FLIGHT, true);
        }

    }

    private void search(HttpServletRequest request) throws ParseException, ServiceException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String from = request.getParameter(PARAM_NAME_SELECT_FROM);
        String to = request.getParameter(PARAM_NAME_SELECT_TO);

        Date dateOut = new Date(formatter.parse(request.getParameter(PARAM_NAME_DATEOUT)).getTime());
        Date dateIn = new Date(formatter.parse(request.getParameter(PARAM_NAME_DATEIN)).getTime());

        String count = request.getParameter(PARAM_NAME_COUNT);
        Boolean isReturn = Boolean.valueOf(request.getParameter(PARAM_NAME_RETURN));
        FlightService flightService = FlightService.getInstance();
        List<Flight> returnFlights;

        if (isReturn) {
            returnFlights = flightService.getByCityName(to, from, dateIn);
            if (returnFlights.isEmpty()) {
                request.setAttribute(ATTRIBUTE_NAME_ERROR_RETURN, true);
            }
            request.setAttribute(ATTRIBUTE_NAME_RETURN_FLIGHTS, returnFlights);
            request.setAttribute(ATTRIBUTE_NAME_IS_RETURN, true);
        }
        String countOfTickets = count.substring(0, 1);
        List<Flight> flights = flightService.getByCityName(from, to, dateOut);
        request.setAttribute(ATTRIBUTE_NAME_CITY_FROM, from);
        request.setAttribute(ATTRIBUTE_NAME_CITY_TO, to);
        request.setAttribute(ATTRIBUTE_NAME_FLIGHTS, flights);
        request.setAttribute(ATTRIBUTE_NAME_COUNT, Integer.valueOf(countOfTickets));
        if (flights.isEmpty()) {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_FLIGHT, true);
        }

    }
}
