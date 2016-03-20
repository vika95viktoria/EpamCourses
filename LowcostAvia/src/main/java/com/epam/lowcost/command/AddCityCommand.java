package com.epam.lowcost.command;

import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.exception.ValidationException;
import com.epam.lowcost.service.CityService;
import com.epam.lowcost.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.lowcost.util.CommandConstants.*;

/**
 * Created by Виктория on 05.03.2016.
 */
public class AddCityCommand extends ActionCommand {

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ValidationException {
        String name = request.getParameter(PARAM_NAME_CITY);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(ATTRIBUTE_NAME_LANGUAGE);
        if (!Validator.validateAddCityForm(name)) {
            request.setAttribute(ERROR_MESSAGE, ServiceMessage.CITY_FAIL.getValue(language));
            throw new ValidationException();
        }

        CityService cityService = CityService.getInstance();
        ServiceMessage message = cityService.createCity(name);
        request.getServletContext().removeAttribute(ATTRIBUTE_NAME_CITIES);
        List<City> cities = cityService.getAllCities();
        request.getServletContext().setAttribute(ATTRIBUTE_NAME_CITIES, cities);
        response.getWriter().print(message.getValue(language));
    }

}

