package by.bsu.lowcost.connection;

import by.bsu.lowcost.exception.ConnectionPoolException;

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
    private static AtomicBoolean created = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final String DRIVER;
    private final int MAX_ACTIVE;
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> busyConnections;
    private AtomicBoolean access = new AtomicBoolean(true);

    private ConnectionPool(String driver, String url, String user, String password, int initialConnections, int maxActive) throws ConnectionPoolException {

        this.DRIVER = driver;
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;
        this.MAX_ACTIVE = maxActive;
        if (initialConnections > this.MAX_ACTIVE) {
            initialConnections = this.MAX_ACTIVE;
        }
        availableConnections = new ArrayBlockingQueue<Connection>(maxActive);
        busyConnections = new ArrayBlockingQueue<Connection>(maxActive);
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException |InstantiationException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
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

    private Connection createNewConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
        return connection;

    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
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
        Connection connection = busyConnections.poll();
        try {
            availableConnections.put(connection);
        } catch (InterruptedException e) {
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
                throw new ConnectionPoolException(e);
            }
        }
        catch (InterruptedException e){
            throw new ConnectionPoolException(e);
        }
    }
}


