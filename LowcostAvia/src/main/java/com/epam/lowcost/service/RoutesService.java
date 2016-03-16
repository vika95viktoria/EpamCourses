package com.epam.lowcost.service;

import com.epam.lowcost.dao.RoutesDAO;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;

import java.util.List;

/**
 * Created by Виктория on 20.02.2016.
 */
public class RoutesService {
    private static RoutesService routesService = new RoutesService();
    private RoutesDAO routesDAO = RoutesDAO.getInstance();

    private RoutesService() {
    }

    public static RoutesService getInstance() {
        return routesService;
    }

    public List<Route> getAllRoutes() throws ServiceException {
        List<Route> routes;
        try {
            routes = routesDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return routes;
    }

    public Route getRouteById(long id) throws ServiceException {
        Route route;
        try {
            route = routesDAO.findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return route;
    }

    public Long createRoute(String cityFrom, String cityTo) throws ServiceException {
        Long routeId;
        try {
            routeId = routesDAO.findRouteIdByCities(cityFrom, cityTo);
            if (routeId == -1) {
                Route route = new Route();
                City from = new City();
                City to = new City();
                from.setName(cityFrom);
                to.setName(cityTo);
                route.setCityFrom(from);
                route.setCityTo(to);
                routesDAO.create(route);
                routeId = routesDAO.findRouteIdByCities(cityFrom, cityTo);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return routeId;
    }
}
