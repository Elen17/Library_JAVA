package com.library.servlets.main;

import com.google.gson.Gson;
import com.library.author.Author;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class HomePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("http://localhost:4200/library/main");
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        resp.addHeader("Access-Control-Allow-Origin", "*");
////        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        try {
//            String url = req.getRequestURL().toString();
//            if (url.endsWith("names")) {
//                Map<Integer, String> names = DBConnectionMSSQL.getInstance().getAuthorsNames();
//                Gson gson = new Gson();
//                resp.getWriter().write(gson.toJson(names));
//            } else if (url.endsWith("author/all")) {
//                List<Author> authors = DBConnectionMSSQL.getInstance().getAllAuthors();
//                Gson gson = new Gson();
//                resp.getWriter().write(gson.toJson(authors));
//            }
//        } catch (
//                SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
