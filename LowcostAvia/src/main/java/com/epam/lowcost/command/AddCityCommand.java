package com.epam.lowcost.command;

import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.CityService;
import com.epam.lowcost.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Виктория on 05.03.2016.
 */
public class AddCityCommand extends ActionCommand {
    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {

        String name = request.getParameter("city");
        if (Validator.validateAddCityForm(name)) {
            CityService cityService = CityService.getInstance();
            ServiceMessage message = cityService.createCity(name);
            request.getServletContext().removeAttribute("cities");
            List<City> cities = cityService.getAllCities();
            request.getServletContext().setAttribute("cities", cities);
            response.getWriter().print(message.getValue());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}

