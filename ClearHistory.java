package com.accio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ClearHistory")
public class ClearHistory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Setting up connection to database
            Connection connection = DatabaseConnection.getConnection();
            //substitute the name of the table {history} without the curly braces {}
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from {history};");
            preparedStatement.executeUpdate();

            response.sendRedirect("http://localhost:8080/SearchEngine/History");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}