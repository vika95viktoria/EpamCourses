package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.domain.City;
import com.epam.lowcost.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 22.02.2016.
 */
public class CityDAO extends AbstractDAO<Long, City> {
    private static final String SQL_SELECT_CITY_BY_ID = "SELECT * FROM cities where id = ?";
    private static final String SQL_INSERT_CITY = "INSERT INTO cities (name)  VALUES (?)";
    private static final String SQL_SELECT_ALL_CITIES = "SELECT * FROM cities ORDER BY name";
    private static final String SQL_SELECT_ID_BY_CITY_FROM = "SELECT idTo FROM routes where idFrom = (select id from cities where name=?)";
    private static CityDAO cityDAO = new CityDAO();


    private CityDAO() {
    }

    public static CityDAO getInstance() {
        return cityDAO;
    }

    @Override
    public City findEntityById(Long id) throws DAOException {
        City city = new City();
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_CITY_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            city.setId(resultSet.getLong("id"));
            city.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return city;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean create(City entity) throws DAOException {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_CITY);
            statement.setString(1, entity.getName());
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
    public City update(City entity) {
        return entity;
    }

    @Override
    public boolean remove(City entity) {
        return false;
    }

    @Override
    public List<City> findAll() throws DAOException {
        List<City> cities = new ArrayList<>();
        Connection connection;
        Statement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CITIES);
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return cities;
    }

    public List<City> findAllForCity(String name) throws DAOException {
        List<City> cities = new ArrayList<>();
        Connection connection;
        PreparedStatement statement = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID_BY_CITY_FROM);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                City city = findEntityById(resultSet.getLong("idTo"));
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return cities;
    }
}
