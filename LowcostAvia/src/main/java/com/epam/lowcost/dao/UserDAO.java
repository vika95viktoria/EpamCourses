package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.connection.ProxyConnection;
import com.epam.lowcost.domain.CreditCard;
import com.epam.lowcost.domain.User;
import com.epam.lowcost.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lowcost.util.DAOConstants.*;

/**
 * Created by Виктория on 25.02.2016.
 */
public class UserDAO extends AbstractDAO<Long, User> {
    private static final String SQL_INSERT_USER_INFO = "INSERT INTO users  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_USER_BY_USERNAME_AND_PASSWORD = "SELECT users.*, creditcard.amount,creditcard.type FROM users join creditcard on users.cardId = creditcard.id where username=? and password = ?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT users.*, creditcard.amount,creditcard.type FROM users join creditcard on users.cardId = creditcard.id where users.id = ?";
    private static final String SQL_SELECT_USER_ID_BY_USERNAME = "SELECT id FROM users where username = ?";
    private static UserDAO userDAO = new UserDAO();

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        return userDAO;
    }

    @Override
    public User findEntityById(Long id) throws DAOException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return user;

    }

    private User createUser(ResultSet resultSet) throws DAOException {
        User user = new User();
        try {
            user.setId(resultSet.getLong(ID));
            user.setUsername(resultSet.getString(USERNAME));
            user.setPassword(resultSet.getString(PASSWORD));
            user.setEmail(resultSet.getString(EMAIL));
            user.setName(resultSet.getString(NAME));
            user.setSurname(resultSet.getString(SURNAME));
            user.setAdmin(resultSet.getBoolean(IS_ADMIN));
            CreditCard creditCard = new CreditCard();
            creditCard.setId(resultSet.getLong(CARD_ID));
            creditCard.setAmount(resultSet.getDouble(AMOUNT));
            creditCard.setType(resultSet.getString(TYPE));
            user.setCard(creditCard);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    /**
     * Return user with given credentials
     *
     * @param username
     * @param password
     * @return
     * @throws DAOException
     */

    public User findByUsernamePassword(String username, String password) throws DAOException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_USER_BY_USERNAME_AND_PASSWORD);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);

        }
        return user;
    }

    /**
     * Check if user with such username exists
     *
     * @param username
     * @return
     * @throws DAOException
     */
    public long persist(String username) throws DAOException {
        long id = -1;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong(ID);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return id;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean create(User entity) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_USER_INFO);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.setBoolean(5, false);
            statement.setString(6, entity.getName());
            statement.setString(7, entity.getSurname());
            statement.setLong(8, entity.getCard().getId());
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
    public User update(User entity) {
        return entity;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }


}

