package nl.besselking.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestMockDBConnection {
    static Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream input = cl.getResourceAsStream("dbTest.properties");
        properties.load(input);
        String drivers = properties.getProperty("jdbc.driverClassName");
        String connectionUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(
                connectionUrl,
                user,
                password
        );
    }
}
