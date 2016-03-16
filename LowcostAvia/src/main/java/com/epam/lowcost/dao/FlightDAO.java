package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.domain.*;
import com.epam.lowcost.exception.DAOException;
import com.epam.lowcost.resource.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Виктория on 24.02.2016.
 */
public class FlightDAO extends AbstractDAO<Long, Flight> {
    private static final String SQL_UPDATE_FLIGHT_INFO = "update flight set economyPrice=?, businessPrice=?,DateOut=?,DateIn=? where id=?";
    private static final String SQL_DELETE_FLIGTHS_BY_ROUTE = "delete FROM flight where id not in (select flightId from tickets) and routeId =? ";
    private static final String SQL_SELECT_FLIGHTS_BY_ROUTEID_AND_DATE = "SELECT id FROM flight where routeId = ? and DateOut=?";
    private static final String SQL_SELECT_FLIGHTS_BY_ROUTEID = "SELECT * FROM flight where routeId = ? and DateOut>=? ORDER BY DateOut";
    private static final String SQL_SELECT_FLIGHT_BY_ID = "SELECT * FROM flight where id = ?";
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
        Connection connection;
        PreparedStatement statement = null;
        RoutesDAO routesDAO = RoutesDAO.getInstance();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            flight.setEconomyPrice(resultSet.getDouble("economyPrice"));
            flight.setBusinessPrice(resultSet.getDouble("businessPrice"));
            flight.setId(resultSet.getLong("id"));
            flight.setRoute(routesDAO.findEntityById(resultSet.getLong("routeId")));
            flight.setBusinessCount(resultSet.getInt("businessCount"));
            flight.setEconomyCount(resultSet.getInt("economyCount"));
            flight.setDateOut(resultSet.getTimestamp("DateOut"));
            flight.setDateIn(resultSet.getTimestamp("DateIn"));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return flight;
    }

    @Override
    public boolean remove(Long id) throws DAOException {
        Connection connection;
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
            ConnectionPool.getInstance().releaseConnection();

        }
        return true;
    }

    private long persist(Flight entity) throws DAOException {
        Long flightId = -1L;
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FLIGHTS_BY_ROUTEID_AND_DATE);
            statement.setLong(1, entity.getRoute().getId());
            statement.setTimestamp(2, new Timestamp(entity.getDateOut().getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flightId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return flightId;
    }

    @Override
    public boolean create(Flight entity) throws DAOException {
        Connection connection;
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
                ConnectionPool.getInstance().releaseConnection();

            }

        }
        return true;
    }

    @Override
    public Flight update(Flight entity) throws DAOException {
        Connection connection;
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
            ConnectionPool.getInstance().releaseConnection();

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
        Connection connection;
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
                flight.setEconomyPrice(resultSet.getDouble("economyPrice"));
                flight.setBusinessPrice(resultSet.getDouble("businessPrice"));
                flight.setId(resultSet.getLong("id"));
                flight.setRoute(routesDAO.findEntityById(resultSet.getLong("routeId")));
                flight.setBusinessCount(resultSet.getInt("businessCount"));
                flight.setEconomyCount(resultSet.getInt("economyCount"));
                flight.setDateOut(resultSet.getTimestamp("DateOut"));
                flight.setDateIn(resultSet.getTimestamp("DateIn"));
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return flights;
    }

    public List<Flight> findAllByCitiesName(String cityFrom, String cityTo, long date) throws DAOException {
        List<Flight> flights = new ArrayList<>();
        Connection connection;
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
                from.setId(resultSet.getLong("idFrom"));
                from.setName(cityFrom);
                to.setId(resultSet.getLong("idTo"));
                to.setName(cityTo);
                route.setId(resultSet.getLong("routeId"));
                route.setCityFrom(from);
                route.setCityTo(to);
                flight.setEconomyPrice(resultSet.getDouble("economyPrice"));
                flight.setBusinessPrice(resultSet.getDouble("businessPrice"));
                flight.setId(resultSet.getLong("id"));
                flight.setRoute(route);
                flight.setBusinessCount(resultSet.getInt("businessCount"));
                flight.setEconomyCount(resultSet.getInt("economyCount"));
                flight.setDateOut(resultSet.getTimestamp("DateOut"));
                flight.setDateIn(resultSet.getTimestamp("DateIn"));
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return flights;
    }

    public Double findMinPrice(Long id) throws DAOException {
        Connection connection;
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
                price = resultSet.getDouble("economyPrice");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return price;

    }

    public void updateTicketCount(Long id, boolean isBusiness) throws DAOException {
        Connection connection;
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
            ConnectionPool.getInstance().releaseConnection();

        }
    }


    public ServiceMessage buyTicket(Long userId, List<TicketModel> ticketModels) throws DAOException {
        ConfigurationManager priceManager = new ConfigurationManager();
        priceManager.loadProperties("price.properties");
        Connection connection = null;
        Connection connection2 = null;
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
                    String luggageKey = "luggage" + model.getLuggage();
                    String priorityKey = "priority";
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
                        double priorityPrice = Double.parseDouble(priceManager.getProperty(priorityKey));
                        price += priorityPrice;
                    }

                    checkTickets.setLong(1, model.getFlightId());
                    getMoney = connection2.prepareStatement(SQL_UPDATE_AMOUNT);
                    getMoney.setLong(1, userId);
                    getMoney.setDouble(2, price);
                    getMoney.setDouble(3, price);
                    if (checkTickets.executeUpdate() == 0) {
                        rollback(connection, connection2);
                        return ServiceMessage.NOTICKET;
                    } else if (getMoney.executeUpdate() == 0) {
                        rollback(connection, connection2);
                        return ServiceMessage.NOMONEY;
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
            ConnectionPool.getInstance().releaseConnection();

        }
        return ServiceMessage.OKBUY;
    }

    private void rollback(Connection connection, Connection connection2) {
        try {
            connection.rollback();
            connection2.rollback();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

}

