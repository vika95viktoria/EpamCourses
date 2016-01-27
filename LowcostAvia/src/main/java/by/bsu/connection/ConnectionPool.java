package by.bsu.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Виктория on 27.01.2016.
 */
public class ConnectionPool {
    private String url, user, password, driver;
    private int maxActive;
    private int maxWait;
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> busyConnections;

    ConnectionPool(String driver, String url, String user, String password, int initialConnections, int maxActive, int maxWait) throws SQLException {

        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        if (initialConnections > maxActive) {
            initialConnections = maxActive;
        }
        availableConnections = new ArrayBlockingQueue<Connection>(initialConnections);
        for (int i = 0; i < initialConnections; i++) {
            availableConnections.add(makeNewConnection());
        }
    }

    private Connection makeNewConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            throw new SQLException("No connection");
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if (!availableConnections.isEmpty()) {
            try {
                connection = availableConnections.poll((long) maxWait, TimeUnit.MILLISECONDS);
                busyConnections.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (maxActive < availableConnections.size() + busyConnections.size()) {
            try {
                availableConnections.put(makeNewConnection());
                getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new SQLException("Connection limit reached");
        }
        return connection;
    }

    public void releaseConnection() {
        Connection connection = busyConnections.poll();
        try {
            availableConnections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeAllConnections() throws SQLException {
        for (Connection connection : availableConnections) {
            connection.close();
        }
        for (Connection connection : busyConnections) {
            connection.close();
        }
    }
}


