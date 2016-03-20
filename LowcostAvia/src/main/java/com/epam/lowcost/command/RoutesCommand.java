package com.epam.lowcost.command;

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
import java.util.List;

/**
 * Created by Виктория on 20.02.2016.
 */
public class RoutesCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.loadProperties(PATH_PROPERTIES_FILE);
        String page;
        FlightService flightService = FlightService.getInstance();
        RoutesService routesService = RoutesService.getInstance();
        List<Route> allRoutes = routesService.getAllRoutes();
        request.setAttribute(ATTRIBUTE_NAME_ROUTES, allRoutes);
        request.setAttribute(ATTRIBUTE_NAME_MIN_PRICE, flightService.getMinPrices(allRoutes));

        String action = request.getParameter(PARAM_NAME_ACTION);
        if (ACTION_NAME_REMOVE.equals(action)) {
            page = configurationManager.getProperty(DELETE_ROUTES_PATH);
        } else {
            page = configurationManager.getProperty(ROUTES_PATH);
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
