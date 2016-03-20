package com.epam.lowcost.init;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.CityService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

import static com.epam.lowcost.util.CommandConstants.ATTRIBUTE_NAME_CITIES;

/**
 * Created by Виктория on 25.02.2016.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        CityService cityService = CityService.getInstance();
        try {
            List<City> cities = cityService.getAllCities();
            sce.getServletContext().setAttribute(ATTRIBUTE_NAME_CITIES, cities);
        } catch (ServiceException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().closeAllConnections();
    }
}
