package com.library.servlets.book;

import com.google.gson.Gson;
import com.library.book.Book;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class GetBook extends HttpServlet {
    private Gson gson = new Gson();
    private DBConnectionMSSQL db = DBConnectionMSSQL.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        String param = req.getParameter("param");
        if (!query.equals("all") && param == null) {
            resp.sendRedirect("http://localhost:4200/library/errorPage?message=paramRequired");
        }
        try {
            switch (query) {
                case "all": {
                    String str = gson.toJson(db.getAllBooks(), HashMap.class);
                    System.out.println(gson.toJson(db.getAllBooks(), HashMap.class));
                    resp.getWriter().write(str);
                    break;
                }
                case "title": {
                    resp.getWriter().write(gson.toJson(db.getBooksByTitle(param), HashMap.class));
                    break;
                }
                case "id": {
                    resp.getWriter().write(gson.toJson(db.getBookById(Integer.parseInt(param)), Book.class));
                    break;
                }
                case "author": {
                    resp.getWriter().write(gson.toJson(gson.toJson(db.getBooksByAuthor(Integer.parseInt(param))), HashMap.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
