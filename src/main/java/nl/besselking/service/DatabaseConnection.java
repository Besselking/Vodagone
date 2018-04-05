package nl.besselking.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DatabaseConnection {
    public DatabaseConnection() {
    }

    public Connection getConn() throws IOException {
        Properties connectionProps = getProperties();

        String drivers = connectionProps.getProperty("jdbc.driverClassName");
        String connectionUrl = connectionProps.getProperty("jdbc.url");
        String user = connectionProps.getProperty("jdbc.username");
        String password = connectionProps.getProperty("jdbc.password");

        try {

            Class.forName(drivers);
            return getConnection(connectionUrl, user, password);

        } catch (SQLException e) {
            System.err.println("Error connecting to database:" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Properties getProperties() throws IOException {
        Properties connectionProps = new Properties();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream  input = cl.getResourceAsStream("db.properties");
        connectionProps.load(input);
        return connectionProps;
    }
}
