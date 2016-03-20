package com.epam.lowcost.dao;

import com.epam.lowcost.connection.ConnectionPool;
import com.epam.lowcost.domain.CreditCard;
import com.epam.lowcost.domain.ServiceMessage;
import com.epam.lowcost.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lowcost.util.DAOConstants.*;

/**
 * Created by Виктория on 25.02.2016.
 */
public class CreditCardDAO extends AbstractDAO<Long, CreditCard> {
    private static final String SQL_INSERT_USER_INFO = "INSERT INTO creditcard  VALUES (?, ?, ?)";
    private static final String SQL_SELECT_CARD_BY_ID = "SELECT * FROM creditcard where id = ?";
    private static final String SQL_UPDATE_AMOUNT = "UPDATE creditcard  join users on creditcard.id = (select users.cardId from users where id = ?) SET creditcard.amount = CASE WHEN creditcard.amount>?  THEN  creditcard.amount-? ELSE creditcard.amount END";
    private static CreditCardDAO creditCardDAO = new CreditCardDAO();

    private CreditCardDAO() {
    }

    public static CreditCardDAO getInstance() {
        return creditCardDAO;
    }

    @Override
    public CreditCard findEntityById(Long id) throws DAOException {
        CreditCard creditCard = new CreditCard();
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_CARD_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            creditCard.setId(resultSet.getLong(ID));
            creditCard.setType(resultSet.getString(TYPE));
            creditCard.setAmount(resultSet.getDouble(AMOUNT));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return creditCard;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean create(CreditCard entity) throws DAOException {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_USER_INFO);
            statement.setLong(1, entity.getId());
            statement.setDouble(2, entity.getAmount());
            statement.setString(3, entity.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return true;
    }

    /**
     * Decreases money amount in database
     *
     * @param price
     * @param userId
     * @return ServiceMessage
     * @throws DAOException
     */

    public ServiceMessage updateAmount(double price, Long userId) throws DAOException {
        Connection connection;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_UPDATE_AMOUNT);
            statement.setLong(1, userId);
            statement.setDouble(2, price);
            statement.setDouble(3, price);
            if (statement.executeUpdate() == 0) {
                connection.rollback();
                return ServiceMessage.NO_MONEY;
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            ConnectionPool.getInstance().releaseConnection();

        }
        return ServiceMessage.OK_EDIT;
    }

    @Override
    public CreditCard update(CreditCard entity) {
        return entity;
    }

    @Override
    public boolean remove(CreditCard entity) {
        return false;
    }

    @Override
    public List<CreditCard> findAll() {
        return null;
    }
}

