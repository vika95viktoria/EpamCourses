package com.epam.lowcost.connection;

import com.epam.lowcost.exception.ConnectionPoolException;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Виктория on 27.01.2016.
 */
public class ConnectionPool {
    private static ConnectionPool connectionPool;
    private static Lock lock = new ReentrantLock();
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final String DRIVER;
    private final int MAX_ACTIVE;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> busyConnections;
    private static AtomicBoolean access = new AtomicBoolean(true);
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool(String driver, String url, String user, String password, int initialConnections, int maxActive) throws ConnectionPoolException {

        this.DRIVER = driver;
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;
        this.MAX_ACTIVE = maxActive;
        if (initialConnections > MAX_ACTIVE) {
            initialConnections = MAX_ACTIVE;
        }
        availableConnections = new ArrayBlockingQueue<ProxyConnection>(maxActive);
        busyConnections = new ArrayBlockingQueue<ProxyConnection>(maxActive);
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException |InstantiationException e) {
            throw new ConnectionPoolException(e);
        }

        for (int i = 0; i < initialConnections; i++) {
            availableConnections.add(createNewConnection());
        }
    }


    public static ConnectionPool getInstance(String driver, String url, String user, String password, int initialConnections, int maxActive, int maxWait) throws ConnectionPoolException {
        if (!created.get()) {
            try {
                lock.lock();
                if (connectionPool == null) {
                    connectionPool = new ConnectionPool(driver, url, user, password, initialConnections, maxActive);
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    private ProxyConnection createNewConnection() throws ConnectionPoolException {
        ProxyConnection connection = null;
        try {
            connection = new ProxyConnection(DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            logger.error(e);
        }
        return connection;

    }

    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection = null;
        if (access.get()) {
            try {
                connection = availableConnections.take();
                busyConnections.put(connection);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException(e);
            }
            if (MAX_ACTIVE > availableConnections.size() + busyConnections.size()) {
                try {
                    availableConnections.put(createNewConnection());
                    getConnection();
                } catch (InterruptedException e) {
                    throw new ConnectionPoolException(e);
                }
            } else {
                throw new ConnectionPoolException("Connection limit reached");
            }
        }
        return connection;
    }

    public void releaseConnection() throws ConnectionPoolException {
        ProxyConnection connection = busyConnections.poll();
        try {
            connection.setAutoCommit(true);
            connection.setReadOnly(true);
            availableConnections.put(connection);
        } catch (InterruptedException | SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public void closeAllConnections() throws ConnectionPoolException {
        access.set(false);
        try {
            TimeUnit.SECONDS.sleep(1);
            try {
                for (Connection connection : availableConnections) {
                    connection.close();
                }
                for (Connection connection : busyConnections) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        catch (InterruptedException e){
            throw new ConnectionPoolException(e);
        }
    }
}


