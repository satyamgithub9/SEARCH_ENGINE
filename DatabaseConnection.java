package com.accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection != null)
            return connection;

        //substitute the username and password of your MySQL database respectively in the string values, without the curly braces {}
        String user = "{username}";
        String pwd = "{password}";
        String db = "search_engine";

        return getConnection(user, pwd, db);
    }

    private static Connection getConnection(String user, String pwd, String db) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);

        return connection;
    }
}
