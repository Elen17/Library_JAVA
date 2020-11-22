package com.library.servlets;


import com.google.gson.Gson;
import com.library.author.Author;
import com.library.db.DBConnectionMySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPage extends HttpServlet {
//    private static DBConnectionMSSQL connection = DBConnectionMSSQL.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        String name = req.getParameter("query");
        Map<Integer, Author> authors = new HashMap<>();
        try {
//            authors = DBConnectionMSSQL.getInstance().getAuthorsByName(name);// work:
            authors = DBConnectionMySQL.getInstance().getAuthorsByName(name);
//                    passQuery(String.format("SELECT * FROM %s WHERE NAME='%s'", "AUTHOR", name));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Authors: " + authors);
        String obj = new Gson().toJson(new ArrayList<>(authors.values()));
        resp.getWriter().print(obj);
    }
}
