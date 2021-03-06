package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.connection.ProxyConnection;
import com.epam.lowcost.domain.*;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.resource.ConfigurationManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.lowcost.util.CommandConstants.*;
import static com.epam.lowcost.util.DAOConstants.*;

/**
 * Created by Виктория on 24.02.2016.
 */
public class FlightDAO extends AbstractDAO<Long, Flight> {
    private static final String SQL_UPDATE_FLIGHT_INFO = "update flight set economyPrice=?, businessPrice=?,DateOut=?,DateIn=? where id=?";
    private static final String SQL_DELETE_FLIGTHS_BY_ROUTE = "delete FROM flight where id not in (select flightId from tickets) and routeId =? ";
    private static final String SQL_SELECT_FLIGHTS_BY_ROUTEID_AND_DATE = "SELECT id FROM flight where routeId = ? and DateOut=?";
    private static final String SQL_SELECT_FLIGHTS_BY_ROUTEID = "SELECT id,routeId,businessCount,economyCount,DateOut,DateIn,economyPrice,businessPrice FROM flight where routeId = ? and DateOut>=? ORDER BY DateOut";
    private static final String SQL_SELECT_FLIGHT_BY_ID = "SELECT id,routeId,businessCount,economyCount,DateOut,DateIn,economyPrice,businessPrice FROM flight where id = ?";
    private static final String SQL_ADD_BUSINESS_COUNT = "UPDATE flight SET businessCount = businessCount+1 where id = ?";
    private static final String SQL_ADD_ECONOMY_COUNT = "UPDATE flight SET economyCount = economyCount+1 where id = ?";
    private static final String SQL_SELECT_FLIGHT_MIN_PRICE = "SELECT MIN(economyPrice) as economyPrice from flight where routeId= ? and DateOut>=?";
    private static final String SQL_UPDATE_BUSINESS_COUNT = "UPDATE flight SET businessCount = CASE WHEN businessCount>0 THEN businessCount-1 ELSE businessCount END where id = ?";
    private static final String SQL_UPDATE_ECONOMY_COUNT = "UPDATE flight SET economyCount = CASE WHEN economyCount>0 THEN economyCount-1 ELSE economyCount END where id = ?";
    private static final String SQL_UPDATE_AMOUNT = "UPDATE creditcard  join users on creditcard.id = (select users.cardId from users where id = ?) SET creditcard.amount = CASE WHEN creditcard.amount>?  THEN  creditcard.amount-? ELSE creditcard.amount END";
    private static final String SQL_SELECT_FLIGHTS_BY_CITIES_NAME = "SELECT flight.id, flight.routeId,flight.businessCount,flight.economyCount,flight.DateOut,flight.DateIn,flight.economyPrice,flight.businessPrice, routes.idFrom, routes.idTo FROM flight join routes on flight.routeId = routes.id  where idFrom = (select id from cities where name = ?) and idTo = (select id from cities where name = ?) and flight.DateOut>=? and flight.DateOut<=? ORDER BY flight.DateOut";
    private static final String SQL_INSERT_FLIGHT = "INSERT INTO flight (routeId, businessCount,economyCount,DateOut,DateIn,economyPrice,businessPrice) VALUES(?,?,?,?,?,?,?)";
    private static FlightDAO flightDAO = new FlightDAO();
    private static Lock lock = new ReentrantLock();
    private final Long TWO_WEEKS = 1209600000L;

    private FlightDAO() {
    }

    public static FlightDAO getInstance() {
        return flightDAO;
    }

    @Override
    public Flight findEntityById(Long id) throws DAOException {
        Flight flight = new Flight();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        RoutesDAO routesDAO = RoutesDAO.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight.setEconomyPrice(resultSet.getDouble(ECONOMY_PRICE));
                flight.setBusinessPrice(resultSet.getDouble(BUSINESS_PRICE));
                flight.setId(resultSet.getLong(ID));
                flight.setRoute(routesDAO.findEntityById(resultSet.getLong(ROUTE_ID)));
                flight.setBusinessCount(resultSet.getInt(BUSINESS_COUNT));
                flight.setEconomyCount(resultSet.getInt(ECONOMY_COUNT));
                flight.setDateOut(resultSet.getTimestamp(DATE_OUT));
                flight.setDateIn(resultSet.getTimestamp(DATE_IN));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return flight;
    }

    @Override
    public boolean remove(Long id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_FLIGTHS_BY_ROUTE);
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

    /**
     * Check if such fligth already existed
     *
     * @param entity
     * @return
     * @throws DAOException
     */
    private long persist(Flight entity) throws DAOException {
        Long flightId = -1L;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHTS_BY_ROUTEID_AND_DATE);
            statement.setLong(1, entity.getRoute().getId());
            statement.setTimestamp(2, new Timestamp(entity.getDateOut().getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flightId = resultSet.getLong(ID);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return flightId;
    }

    @Override
    public boolean create(Flight entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        if (persist(entity) == -1) {
            try {
                connection = ConnectionPool.getInstance().getConnection();
                statement = connection.prepareStatement(SQL_INSERT_FLIGHT);
                statement.setLong(1, entity.getRoute().getId());
                statement.setInt(2, entity.getBusinessCount());
                statement.setInt(3, entity.getEconomyCount());
                statement.setTimestamp(4, new Timestamp(entity.getDateOut().getTime()));
                statement.setTimestamp(5, new Timestamp(entity.getDateIn().getTime()));
                statement.setDouble(6, entity.getEconomyPrice());
                statement.setDouble(7, entity.getBusinessPrice());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                close(statement);
                ConnectionPool.getInstance().releaseConnection(connection);

            }

        }
        return true;
    }

    @Override
    public Flight update(Flight entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_FLIGHT_INFO);
            statement.setDouble(1, entity.getEconomyPrice());
            statement.setDouble(2, entity.getBusinessPrice());
            statement.setTimestamp(3, new Timestamp(entity.getDateOut().getTime()));
            statement.setTimestamp(4, new Timestamp(entity.getDateIn().getTime()));
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return entity;
    }

    @Override
    public boolean remove(Flight entity) {
        return false;
    }

    @Override
    public List<Flight> findAll() {
        return null;
    }

    public List<Flight> findAllByRouteId(Long id) throws DAOException {
        List<Flight> flights = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        RoutesDAO routesDAO = RoutesDAO.getInstance();
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHTS_BY_ROUTEID);
            Date date = new Date();
            statement.setLong(1, id);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setEconomyPrice(resultSet.getDouble(ECONOMY_PRICE));
                flight.setBusinessPrice(resultSet.getDouble(BUSINESS_PRICE));
                flight.setId(resultSet.getLong(ID));
                flight.setRoute(routesDAO.findEntityById(resultSet.getLong(ROUTE_ID)));
                flight.setBusinessCount(resultSet.getInt(BUSINESS_COUNT));
                flight.setEconomyCount(resultSet.getInt(ECONOMY_COUNT));
                flight.setDateOut(resultSet.getTimestamp(DATE_OUT));
                flight.setDateIn(resultSet.getTimestamp(DATE_IN));
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return flights;
    }

    public List<Flight> findAllByCitiesName(String cityFrom, String cityTo, long date) throws DAOException {
        List<Flight> flights = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        Date dateFrom = new Date(date - TWO_WEEKS);
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        if (dateFrom.before(currentDate)) {
            dateFrom = currentDate;
        }
        Date dateTo = new Date(date + TWO_WEEKS);
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHTS_BY_CITIES_NAME);
            statement.setString(1, cityFrom);
            statement.setString(2, cityTo);
            statement.setTimestamp(3, new Timestamp(dateFrom.getTime()));
            statement.setTimestamp(4, new Timestamp(dateTo.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight();
                City from = new City();
                City to = new City();
                Route route = new Route();
                from.setId(resultSet.getLong(ID_FROM));
                from.setName(cityFrom);
                to.setId(resultSet.getLong(ID_TO));
                to.setName(cityTo);
                route.setId(resultSet.getLong(ROUTE_ID));
                route.setCityFrom(from);
                route.setCityTo(to);
                flight.setEconomyPrice(resultSet.getDouble(ECONOMY_PRICE));
                flight.setBusinessPrice(resultSet.getDouble(BUSINESS_PRICE));
                flight.setId(resultSet.getLong(ID));
                flight.setRoute(route);
                flight.setBusinessCount(resultSet.getInt(ECONOMY_COUNT));
                flight.setEconomyCount(resultSet.getInt(BUSINESS_COUNT));
                flight.setDateOut(resultSet.getTimestamp(DATE_OUT));
                flight.setDateIn(resultSet.getTimestamp(DATE_IN));
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return flights;
    }

    /**
     * Find min economy price for this route
     *
     * @param id
     * @return
     * @throws DAOException
     */
    public Double findMinPrice(Long id) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        Double price = 0d;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHT_MIN_PRICE);
            statement.setLong(1, id);
            Date date = new Date();
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                price = resultSet.getDouble(ECONOMY_PRICE);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return price;

    }

    public void updateTicketCount(Long id, boolean isBusiness) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            if (isBusiness) {
                statement = connection.prepareStatement(SQL_ADD_BUSINESS_COUNT);
            } else {
                statement = connection.prepareStatement(SQL_ADD_ECONOMY_COUNT);
            }
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
    }

    /**
     * Check amount of seats on flight and money on user's card. If everything is ok, decreases amount of seats and money on card
     *
     * @param userId
     * @param ticketModels
     * @return ServiceMessage
     * @throws DAOException
     */

    public ServiceMessage buyTicket(Long userId, List<TicketModel> ticketModels) throws DAOException {
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties(PRICE_PROPERTIES_FILE);
        ProxyConnection connection = null;
        ProxyConnection connection2 = null;
        PreparedStatement checkTickets = null;
        PreparedStatement getMoney = null;
        try {
            lock.lock();
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            connection2 = pool.getConnection();
            connection.setAutoCommit(false);
            connection2.setAutoCommit(false);
            for (TicketModel model : ticketModels) {
                for (int i = 0; i < model.getNames().size(); i++) {
                    double price = model.getPrice();
                    String luggageKey = PARAM_NAME_LUGGAGE + model.getLuggage();
                    if (model.getIsBusiness()) {
                        checkTickets = connection.prepareStatement(SQL_UPDATE_BUSINESS_COUNT);
                    } else {
                        checkTickets = connection.prepareStatement(SQL_UPDATE_ECONOMY_COUNT);
                    }
                    if (i < model.getLuggageCount()) {
                        double luggagePrice = Double.parseDouble(priceManager.getProperty(luggageKey));
                        price += luggagePrice;
                    }
                    if (i < model.getPriorityCount()) {
                        double priorityPrice = Double.parseDouble(priceManager.getProperty(PRIORITY_PRICE));
                        price += priorityPrice;
                    }

                    checkTickets.setLong(1, model.getFlightId());
                    getMoney = connection2.prepareStatement(SQL_UPDATE_AMOUNT);
                    getMoney.setLong(1, userId);
                    getMoney.setDouble(2, price);
                    getMoney.setDouble(3, price);
                    if (checkTickets.executeUpdate() == 0) {
                        rollback(connection, connection2);
                        return ServiceMessage.NO_TICKET;
                    } else if (getMoney.executeUpdate() == 0) {
                        rollback(connection, connection2);
                        return ServiceMessage.NO_MONEY;
                    }
                }
            }
            connection.commit();
            connection2.commit();
        } catch (SQLException e) {
            rollback(connection, connection2);
            throw new DAOException(e);
        } finally {
            lock.unlock();
            close(checkTickets);
            close(getMoney);
            ConnectionPool.getInstance().releaseConnection(connection);
            ConnectionPool.getInstance().releaseConnection(connection2);

        }
        return ServiceMessage.OK_BUY;
    }

    private void rollback(ProxyConnection connection, ProxyConnection connection2) throws DAOException {
        try {
            connection.rollback();
            connection2.rollback();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}

