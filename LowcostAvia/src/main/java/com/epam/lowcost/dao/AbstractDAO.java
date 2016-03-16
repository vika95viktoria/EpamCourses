package com.epam.lowcost.dao;

import com.epam.lowcost.domain.Entity;
import com.epam.lowcost.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Виктория on 22.02.2016.
 */
public abstract class AbstractDAO<K, T extends Entity> {
    private static Logger logger = Logger.getLogger(AbstractDAO.class);

    public abstract List<T> findAll() throws DAOException;

    public abstract T findEntityById(K id) throws DAOException;

    public abstract boolean remove(K id) throws DAOException;

    public abstract boolean remove(T entity) throws DAOException;

    public abstract boolean create(T entity) throws DAOException;

    public abstract T update(T entity) throws DAOException;

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
