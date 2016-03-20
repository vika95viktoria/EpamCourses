package com.epam.lowcost.service;

import com.epam.lowcost.dao.CityDAO;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;

import java.util.List;

/**
 * Created by Виктория on 27.02.2016.
 */
public class CityService {
    private static CityService cityService = new CityService();
    CityDAO cityDAO = CityDAO.getInstance();

    private CityService() {
    }

    public static CityService getInstance() {
        return cityService;
    }

    public List<City> getAllCities() throws ServiceException {
        List<City> cities;
        try {
            cities = cityDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cities;
    }

    public List<City> getAllCitiesFor(String name) throws ServiceException {
        List<City> cities;
        try {
            cities = cityDAO.findAllForCity(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cities;
    }

    public ServiceMessage createCity(String name) throws ServiceException {
        City city = new City();
        city.setName(name);
        try {
            if (!cityDAO.create(city)) {
                return ServiceMessage.CITY_FAIL;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return ServiceMessage.CITY_OK;
    }
}
