package com.epam.lowcost.command;

import com.epam.lowcost.domain.City;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.service.CityService;
import static com.epam.lowcost.util.CommandConstants.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Виктория on 05.03.2016.
 */
public class CityToCommand extends ActionCommand {

    @Override
    public void action(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        String name = request.getParameter(PARAM_NAME_CITY_FROM);
        ObjectMapper mapper = new ObjectMapper();
        CityService cityService = CityService.getInstance();
        List<City> cities = cityService.getAllCitiesFor(name);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cities);
        response.getWriter().print(json);

    }
}
