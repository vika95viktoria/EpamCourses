package com.epam.lowcost;

import com.epam.lowcost.connection.ConnectionPool;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виктория on 20.03.2016.
 */
public class ConnectionPoolTest {

    @Test(expected = MySQLNonTransientConnectionException.class)
    public void testPoolSize() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            connections.add(pool.getConnection());
        }
        Assert.assertEquals(10, connections.size());
        pool.closeAllConnections();
        connections.get(1).commit();
    }

}
