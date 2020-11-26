package com.library.servlets;

import com.google.gson.Gson;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class HomePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect("http://localhost:4200/main");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        resp.setHeader("Content-type", ""); text-json
        try {
            if (req.getRequestURL().toString().endsWith("names")) {
                List<String> names = DBConnectionMSSQL.getInstance().getAuthorsNames();
                Gson gson = new Gson();
                resp.getWriter().write(gson.toJson(names));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
