package com.library.servlets;


import com.google.gson.Gson;
import com.library.author.Address;
import com.library.author.Author;
import com.library.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends HttpServlet {
    private static DBConnection connection = DBConnection.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        String name = req.getParameter("query");
        ResultSet result = null;
        try {
            result = connection.passQuery(String.format("SELECT * FROM %s WHERE NAME='%s'", "AUTHOR", name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Author> authors = new ArrayList<>();
        if(result != null) {
            try {
                while (result.next()) {
                    LocalDate date = result.getDate("BIRTH_DATE").toLocalDate();
                    System.out.println("Result:  " +  result);
                    authors.add(new Author(result.getInt("AUTHOR_ID"), result.getString("name"), result.getString("surname"),
                            date, null,
                            new Address(result.getString("BIRTH_COUNTRY"), result.getString("BIRTH_City"))));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Authors: " + authors);
        String obj = new Gson().toJson(authors);
        resp.getWriter().print(obj);
    }
}
