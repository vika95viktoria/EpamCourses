package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.connection.ProxyConnection;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.lowcost.util.DAOConstants.*;

/**
 * Created by Виктория on 22.02.2016.
 */
public class RoutesDAO extends AbstractDAO<Long, Route> {
    private static final String SQL_SELECT_ALL_ROUTES = "SELECT routes.*, cities.id as cityId, cities.name FROM routes join cities on routes.idFrom = cities.id";
    private static final String SQL_SELECT_ROUTE_BY_ID = "SELECT cities.id,cities.name FROM routes join cities on cities.id = routes.idFrom where routes.id=? union  (SELECT  cities.id,cities.name FROM routes join cities on cities.id = routes.idTo where routes.id=?)";
    private static final String SQL_SELECT_ROUTE_BY_CITIES = "SELECT id,idFrom,idTo FROM routes where idFrom = (select id from cities where name = ?) and idTo = (select id from cities where name = ?)";
    private static final String SQL_CREATE_ROUTE_BY_CITIES = "insert into routes (idFrom,idTo) values ( (select id from cities where name = ?) , (select id from cities where name = ?))";
    private static RoutesDAO routesDAO = new RoutesDAO();

    private RoutesDAO() {
    }

    public static RoutesDAO getInstance() {
        return routesDAO;
    }

    @Override
    public List<Route> findAll() throws DAOException {
        List<Route> routes = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ROUTES);
            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong(ID));
                City cityFrom = new City();
                cityFrom.setId(resultSet.getLong(ID_FROM));
                cityFrom.setName(resultSet.getString(NAME));
                CityDAO cityDAO = CityDAO.getInstance();
                route.setCityFrom(cityFrom);
                route.setCityTo(cityDAO.findEntityById(resultSet.getLong(ID_TO)));
                routes.add(route);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return routes;
    }

    @Override
    public Route findEntityById(Long id) throws DAOException {
        Route route = new Route();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ROUTE_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                route.setId(id);
                City cityFrom = new City();
                City cityTo = new City();
                cityFrom.setId(resultSet.getLong(ID));
                cityFrom.setName(resultSet.getString(NAME));
                resultSet.next();
                cityTo.setId(resultSet.getLong(ID));
                cityTo.setName(resultSet.getString(NAME));
                route.setCityFrom(cityFrom);
                route.setCityTo(cityTo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return route;

    }

    /**
     * Find route id for cities
     *
     * @param cityFrom
     * @param cityTo
     * @return long
     * @throws DAOException
     */
    public long findRouteIdByCities(String cityFrom, String cityTo) throws DAOException {
        Long routeId = -1L;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ROUTE_BY_CITIES);
            statement.setString(1, cityFrom);
            statement.setString(2, cityTo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routeId = resultSet.getLong(ID);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return routeId;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean create(Route entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_CREATE_ROUTE_BY_CITIES);
            statement.setString(1, entity.getCityFrom().getName());
            statement.setString(2, entity.getCityTo().getName());
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
    public Route update(Route entity) {
        return entity;
    }

    @Override
    public boolean remove(Route entity) {
        return false;
    }
}

