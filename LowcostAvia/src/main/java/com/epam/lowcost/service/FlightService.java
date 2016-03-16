package com.epam.lowcost.service;

import com.epam.lowcost.dao.FlightDAO;
import com.epam.lowcost.dao.TicketDAO;
import com.epam.lowcost.domain.Flight;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.domain.TicketModel;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.exception.ServiceException;
import com.epam.lowcost.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Виктория on 20.02.2016.
 */
public class FlightService {
    private static FlightService flightService = new FlightService();
    private FlightDAO flightDAO = FlightDAO.getInstance();
    private TicketDAO ticketDAO = TicketDAO.getInstance();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return flightService;
    }

    public List<Flight> getAllRoutes(Long id) throws ServiceException {
        List<Flight> flights;
        try {
            flights = flightDAO.findAllByRouteId(id);
            editPrices(flights);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return flights;
    }

    public void generateFlights(Flight flight, Date startDate, Date startDateArrival, Date finishDate, String[] days) throws ServiceException {
        try {
            for (int i = 0; i < days.length; i++) {
                while (flight.getDateOut().before(finishDate)) {
                    flightDAO.create(flight);
                    flight.setDateOut(DateUtils.addWeek(flight.getDateOut()));
                    flight.setDateIn(DateUtils.addWeek(flight.getDateIn()));
                }
                if (i < days.length - 1) {
                    int delta = Math.abs(Integer.valueOf(days[i]) - Integer.valueOf(days[i + 1]));
                    flight.setDateOut(DateUtils.addDays(startDate, delta));
                    flight.setDateIn(DateUtils.addDays(startDateArrival, delta));
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Double> getMinPrices(List<Route> all) throws ServiceException {
        List<Double> prices = new ArrayList<>();
        try {
            for (Route route : all) {
                prices.add(flightDAO.findMinPrice(route.getId()));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return prices;
    }

    private void editPrices(List<Flight> flights) throws ServiceException {
        try {
            for (Flight flight : flights) {
                double businessPrice = ticketDAO.getTicketInfo(flight.getId(), true).generatePrice(flight.getBusinessPrice());
                double economyPrice = ticketDAO.getTicketInfo(flight.getId(), false).generatePrice(flight.getEconomyPrice());
                flight.setEconomyPrice(economyPrice);
                flight.setBusinessPrice(businessPrice);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void update(Flight flight) throws ServiceException {
        try {
            flightDAO.update(flight);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Flight> getByCityName(String from, String to, Date dateOut) throws ServiceException {
        List<Flight> flights;
        try {
            flights = flightDAO.findAllByCitiesName(from, to, dateOut.getTime());
            editPrices(flights);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return flights;
    }

    public ServiceMessage buyTicket(Long userId, List<TicketModel> ticketModels) throws ServiceException {
        ServiceMessage message;
        try {
            message = flightDAO.buyTicket(userId, ticketModels);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return message;
    }

    public void deleteFlights(String[] ids) throws ServiceException {
        try {
            for (String id : ids) {
                flightDAO.remove(Long.valueOf(id));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void returnTicket(boolean isBusiness, Long flightId) throws ServiceException {
        try {
            flightDAO.updateTicketCount(flightId, isBusiness);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
