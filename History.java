package com.accio;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

@WebServlet("/History")
public class History extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Setting up connection to database
            Connection connection = DatabaseConnection.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("Select * from history;");
            ArrayList<HistoryResult> results = new ArrayList<>();

            //Transferring values from resultSet to results ArrayList
            while (resultSet.next()) {
                HistoryResult historyResult = new HistoryResult();
                historyResult.setKeyword(resultSet.getString("keyword"));
                historyResult.setLink(resultSet.getString("link"));
                results.add(historyResult);
            }

//            for (HistoryResult result : results)
//                System.out.println(result.getKeyword() + "\n" + result.getLink() + "\n");

            request.setAttribute("results", results);
            request.getRequestDispatcher("history.jsp").forward(request, response);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        } catch (SQLException | ClassNotFoundException | ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
