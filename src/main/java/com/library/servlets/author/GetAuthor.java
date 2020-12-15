package com.library.servlets.author;

import com.google.gson.Gson;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;

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
                    String[] fullName = req.getParameter("param").split("\\s+");
                    System.out.println(Arrays.toString(fullName));
                    String json = gson.toJson(DBConnectionMSSQL.getInstance().getAuthorsByFullName(fullName[0].trim(), fullName[1].trim()));
                    System.out.println(json);
                    resp.getWriter().write(json);
                    break;
                }
                case "all":{
                    resp.getWriter().write(gson.toJson(DBConnectionMSSQL.getInstance().getAllAuthors()));
                    break;
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
