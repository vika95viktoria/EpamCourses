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

    private ConnectionPool() {
        ConfigurationManager dbManager = new ConfigurationManager();
        dbManager.loadProperties("db.properties");
        DRIVER = dbManager.getProperty("driver");
        URL = dbManager.getProperty("url");
        USER = dbManager.getProperty("user");
        PASSWORD = dbManager.getProperty("password");
        MAX_ACTIVE = Integer.valueOf(dbManager.getProperty("maxActive"));
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

    public void releaseConnection() {
        ProxyConnection connection = busyConnections.poll();
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

    public void closeAllConnections() {
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
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}


