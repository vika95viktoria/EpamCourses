package com.epam.lowcost.command;

import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.FlightService;
import com.epam.lowcost.service.RoutesService;

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
        String page;
        FlightService flightService = FlightService.getInstance();
        RoutesService routesService = RoutesService.getInstance();
        List<Route> allRoutes = routesService.getAllRoutes();
        request.setAttribute("routes", allRoutes);
        request.setAttribute("minPrice", flightService.getMinPrices(allRoutes));

        String action = request.getParameter("action");
        if ("remove".equals(action)) {
            page = "/jsp/deleteRoute.jsp";
        } else {
            page = "/jsp/routes.jsp";
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }
}
