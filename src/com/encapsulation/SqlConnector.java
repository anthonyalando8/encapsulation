package com.encapsulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class SqlConnector {
    public SqlConnector() {
    }

    public Connection createConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/LMS?useSSL=true&requireSSL=true&verifyServerCertificate=false&createDatabaseIfNotExist=true";
        String username = "usr";
        String password = "2000";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}