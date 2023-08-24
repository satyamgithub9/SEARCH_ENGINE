package com.accio;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Getting keyword from frontend
        String keyword = request.getParameter("keyword");

        try {
            //Setting up connection to database
            Connection connection = DatabaseConnection.getConnection();

            //Store the query of the user as history
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngine/Search?keyword=" + keyword);
            preparedStatement.executeUpdate();

            //Getting results after running ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select pageTitle, pageLink, (length(lower(pageText)) - length(replace(lower(pageText), '" + keyword.toLowerCase() + "', '')))/length('" + keyword + "') as countOccurrences from pages order by countOccurrences desc limit 30");
            ArrayList<SearchResult> results = new ArrayList<>();

            //Transferring values from resultSet to results ArrayList
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

//            for (SearchResult result : results)
//                System.out.println(result.getTitle() + "\n" + result.getLink() + "\n");

            request.setAttribute("results", results);
            request.getRequestDispatcher("search.jsp").forward(request, response);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        } catch (SQLException | ClassNotFoundException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
