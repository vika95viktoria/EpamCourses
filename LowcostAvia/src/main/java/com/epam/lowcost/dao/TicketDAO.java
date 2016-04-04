package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.connection.ProxyConnection;
import com.epam.lowcost.domain.*;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.util.PriceGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.lowcost.util.DAOConstants.*;

/**
 * Created by Виктория on 29.02.2016.
 */
public class TicketDAO extends AbstractDAO<Long, Ticket> {
    private static final String SQL_SELECT_ALL_TICKETS = "SELECT tickets.id,tickets.luggage,tickets.passName,tickets.passSurname,tickets.isBusiness,tickets.priority,flight.DateOut,flight.DateIn,tickets.flightId, routes.idTo, cities.name FROM tickets join flight on tickets.flightId = flight.id join routes on routes.id = flight.routeId join cities on cities.id = routes.idFrom  where tickets.userId=? and DateOut>? ORDER BY flight.DateOut";
    private static final String SQL_DELETE_TICKET = "DELETE FROM tickets where id=?";
    private static final String SQL_INSERT_TICKET = "INSERT INTO tickets (flightId, isBusiness, luggage,priority, userId,passName, passSurname) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_LUGGAGE_PRIORITY = "UPDATE tickets SET priority = ?, luggage=? where id = ?";
    private static final String SQL_SELECT_COUNT_OF_BUSINESS_TICKETS_ON_FLIGHT = "SELECT count(*) as count, flight.businessCount, flight.DateOut FROM tickets join flight on flight.id = tickets.flightId  where  tickets.isBusiness=1 and tickets.flightId = ?";
    private static final String SQL_SELECT_COUNT_OF_ECONOMY_TICKETS_ON_FLIGHT = "SELECT count(*) as count, flight.economyCount, flight.DateOut FROM tickets join flight on flight.id = tickets.flightId  where  tickets.isBusiness=0 and tickets.flightId = ?";
    private static TicketDAO ticketDAO = new TicketDAO();

    private TicketDAO() {
    }

    public static TicketDAO getInstance() {
        return ticketDAO;
    }

    @Override
    public Ticket findEntityById(Long id) {
        return null;
    }

    @Override
    public boolean remove(Long id) throws DAOException {
        ProxyConnection connection=null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_TICKET);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return true;
    }

    @Override
    public boolean create(Ticket entity) {
        return false;
    }

    /**
     * Add tickets to database
     *
     * @param models
     * @param userId
     * @throws DAOException
     */

    public void createTickets(List<TicketModel> models, Long userId) throws DAOException {
        ProxyConnection connection=null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_TICKET);
            for (TicketModel model : models) {
                for (int i = 0; i < model.getNames().size(); i++) {
                    statement.setLong(1, model.getFlightId());
                    statement.setBoolean(2, model.getIsBusiness());
                    if (i < model.getLuggageCount()) {
                        statement.setInt(3, model.getLuggage());
                    } else {
                        statement.setInt(3, 0);
                    }
                    if (i < model.getPriorityCount()) {
                        statement.setBoolean(4, true);
                    } else {
                        statement.setBoolean(4, false);
                    }
                    statement.setLong(5, userId);
                    statement.setString(6, model.getNames().get(i));
                    statement.setString(7, model.getSurnames().get(i));
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
    }

    /**
     * Add luggage or priority for ticket after purchase
     *
     * @param luggage
     * @param priority
     * @param ticketId
     * @throws DAOException
     */
    public void updateLuggageAndPriority(int luggage, int priority, Long ticketId) throws DAOException {
        ProxyConnection connection=null;
        PreparedStatement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_LUGGAGE_PRIORITY);
            statement.setInt(1, priority);
            statement.setInt(2, luggage);
            statement.setLong(3, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
    }

    /**
     * Return all necessary info for counting price
     *
     * @param flightId
     * @param isBusiness
     * @return
     * @throws DAOException
     */

    public PriceGenerator getTicketInfo(Long flightId, boolean isBusiness) throws DAOException {
        ProxyConnection connection=null;
        PreparedStatement statement = null;
        PriceGenerator priceGenerator = new PriceGenerator();
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            if (!isBusiness) {
                statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ECONOMY_TICKETS_ON_FLIGHT);
            } else {
                statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_BUSINESS_TICKETS_ON_FLIGHT);
            }
            statement.setLong(1, flightId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                priceGenerator.setPurchasedTickets(resultSet.getInt(COUNT));
                if (!isBusiness) {
                    priceGenerator.setRemainedTickets(resultSet.getInt(ECONOMY_COUNT));
                } else {
                    priceGenerator.setRemainedTickets(resultSet.getInt(BUSINESS_COUNT));
                }
                priceGenerator.setDateOut(resultSet.getTimestamp(DATE_OUT));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return priceGenerator;
    }


    @Override
    public Ticket update(Ticket entity) {
        return entity;
    }

    @Override
    public boolean remove(Ticket entity) {
        return false;
    }

    /**
     * Get all tickets for user
     *
     * @param userId
     * @return
     * @throws DAOException
     */

    public List<Ticket> findAllForUserId(Long userId) throws DAOException {
        List<Ticket> tickets = new ArrayList<>();
        ProxyConnection connection=null;
        PreparedStatement statement = null;
        CityDAO cityDAO = CityDAO.getInstance();
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_TICKETS);
            statement.setLong(1, userId);
            statement.setTimestamp(2, new Timestamp(new Date().getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getLong(ID));
                ticket.setLuggage(resultSet.getInt(LUGGAGE));
                ticket.setName(resultSet.getString(PASSENGER_NAME));
                ticket.setSurname(resultSet.getString(PASSENGER_SURNAME));
                ticket.setIsBusiness(resultSet.getBoolean(IS_BUSINESS));
                ticket.setHasPriority(resultSet.getBoolean(PRIORITY));
                Flight flight = new Flight();
                flight.setId(resultSet.getLong(FLIGHT_ID));
                flight.setDateIn(resultSet.getTimestamp(DATE_IN));
                flight.setDateOut(resultSet.getTimestamp(DATE_OUT));
                Route route = new Route();
                City cityFrom = new City();
                cityFrom.setName(resultSet.getString(NAME));
                City cityTo = cityDAO.findEntityById(resultSet.getLong(ID_TO));
                route.setCityTo(cityTo);
                route.setCityFrom(cityFrom);
                flight.setRoute(route);
                ticket.setFlight(flight);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }
}

