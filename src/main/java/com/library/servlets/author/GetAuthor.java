package com.library.servlets.author;

import com.google.gson.Gson;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class GetAuthor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query") ==  null ? "" : req.getParameter("query");
        Gson gson = new Gson();
        try {
            switch (query) {
                case "fullName": {
                    resp.getWriter().write(gson.toJson(DBConnectionMSSQL.getInstance().getAuthorsNames()));
                    break;
                }
                case "name": {
                    resp.getWriter().write(gson.toJson(DBConnectionMSSQL.getInstance().getAuthorsByName(req.getParameter("param"))));
                }
                default: {
                    break;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(req.getParameter("param") + req.getParameter("query"));

    }
}
