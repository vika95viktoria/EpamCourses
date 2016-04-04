package com.epam.lowcost.connection;

import com.epam.lowcost.resource.ConfigurationManager;
import org.apache.log4j.Logger;

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
    private static final String PROPERTIES_FILE = "db.properties";
    private static final String DRIVER_NAME = "driver";
    private static final String URL_ADDRESS = "url";
    private static final String USERNAME = "user";
    private static final String PASS = "password";
    private static final String MAX_ACTIVE_COUNT = "maxActive";
    private static ConnectionPool connectionPool;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean access = new AtomicBoolean(true);
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static Logger logger = Logger.getLogger(ConnectionPool.class);
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final String DRIVER;
    private final int MAX_ACTIVE;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> busyConnections;

    /**
     * Load properties form property file with configuration manager, create pool
     */
    private ConnectionPool() {
        ConfigurationManager dbManager = new ConfigurationManager();
        dbManager.loadProperties(PROPERTIES_FILE);
        DRIVER = dbManager.getProperty(DRIVER_NAME);
        URL = dbManager.getProperty(URL_ADDRESS);
        USER = dbManager.getProperty(USERNAME);
        PASSWORD = dbManager.getProperty(PASS);
        MAX_ACTIVE = Integer.valueOf(dbManager.getProperty(MAX_ACTIVE_COUNT));
        availableConnections = new ArrayBlockingQueue<>(MAX_ACTIVE);
        busyConnections = new ArrayBlockingQueue<>(MAX_ACTIVE);
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            logger.error(e);
        }

        for (int i = 0; i < MAX_ACTIVE; i++) {
            availableConnections.add(createNewConnection());
        }
    }

    /**
     * Create connection pool if it is not created, return instance
     *
     * @return ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (connectionPool == null) {
                    connectionPool = new ConnectionPool();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
            if (connectionPool == null) {
                throw new RuntimeException();
            }
        }
        return connectionPool;
    }


    private ProxyConnection createNewConnection() {
        ProxyConnection connection = null;
        try {
            connection = new ProxyConnection(DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException e) {
            logger.error(e);
        }
        return connection;

    }

    /**
     * Get connection from available, add it to busy connection
     *
     * @return Connection
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        if (access.get()) {
            try {
                connection = availableConnections.take();
                busyConnections.put(connection);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        return connection;
    }

    /**
     * Get connection from busy, add to available, check autocommit and read-only properties
     */
    public void releaseConnection(ProxyConnection connection) {
        if (connection != null) {
            busyConnections.remove(connection);
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                if (!connection.isReadOnly()) {
                    connection.setReadOnly(true);
                }
                availableConnections.put(connection);
            } catch (InterruptedException | SQLException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Destroy pool
     */
    public void closeAllConnections() {
        access.set(false);
        try {
            TimeUnit.SECONDS.sleep(1);
            try {
                for (ProxyConnection connection : availableConnections) {
                    if (connection != null) {
                        connection.closeConnection();
                    }
                }
                for (ProxyConnection connection : busyConnections) {
                    if (connection != null) {
                        connection.closeConnection();
                    }
                }
            } catch (SQLException e) {
                logger.error(e);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}


