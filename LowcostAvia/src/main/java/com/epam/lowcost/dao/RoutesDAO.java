package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.domain.Route;
import com.epam.lowcost.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 22.02.2016.
 */
public class RoutesDAO extends AbstractDAO<Long, Route> {
    private static final String SQL_SELECT_ALL_ROUTES = "SELECT routes.*, cities.id as cityId, cities.name FROM routes join cities on routes.idFrom = cities.id";
    private static final String SQL_SELECT_ROUTE_BY_ID = "SELECT cities.id,cities.name FROM routes join cities on cities.id = routes.idFrom where routes.id=? union  (SELECT  cities.id,cities.name FROM routes join cities on cities.id = routes.idTo where routes.id=?)";
    private static final String SQL_SELECT_ROUTE_BY_CITIES = "SELECT * FROM routes where idFrom = (select id from cities where name = ?) and idTo = (select id from cities where name = ?)";
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
        Connection connection;
        Statement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(SQL_SELECT_ALL_ROUTES);
            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                City cityFrom = new City();
                cityFrom.setId(resultSet.getLong("idFrom"));
                cityFrom.setName(resultSet.getString("name"));
                CityDAO cityDAO = CityDAO.getInstance();
                route.setCityFrom(cityFrom);
                route.setCityTo(cityDAO.findEntityById(resultSet.getLong("idTo")));
                routes.add(route);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return routes;
    }

    @Override
    public Route findEntityById(Long id) throws DAOException {
        Route route = new Route();
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ROUTE_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            route.setId(id);
            City cityFrom = new City();
            City cityTo = new City();
            cityFrom.setId(resultSet.getLong("id"));
            cityFrom.setName(resultSet.getString("name"));
            resultSet.next();
            cityTo.setId(resultSet.getLong("id"));
            cityTo.setName(resultSet.getString("name"));
            route.setCityFrom(cityFrom);
            route.setCityTo(cityTo);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();
        }
        return route;

    }

    public long findRouteIdByCities(String cityFrom, String cityTo) throws DAOException {
        Long routeId = -1L;
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ROUTE_BY_CITIES);
            statement.setString(1, cityFrom);
            statement.setString(2, cityTo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routeId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return routeId;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean create(Route entity) throws DAOException {
        Connection connection;
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
            ConnectionPool.getInstance().releaseConnection();

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
