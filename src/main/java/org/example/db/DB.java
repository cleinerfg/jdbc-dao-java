package org.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("url");
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (InputStream input = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new DbException("File db.properties was not found in src/main/resources!");
            }
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
