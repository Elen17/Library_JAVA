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
import java.util.Scanner;

public class UpdateBook extends HttpServlet {
    private DBConnectionMSSQL dbConnectionMSSQL = DBConnectionMSSQL.getInstance();
    private Gson gson = new Gson();
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String bookID = (req.getParameter("param"));
            if(bookID != null) {
                StringBuilder content = new StringBuilder();
                try (Scanner in = new Scanner(req.getInputStream())) {
                    while (in.hasNextLine()) {
                        content.append(in.nextLine());
                    }
                }
                Book book = gson.fromJson(content.toString(), Book.class);
                System.out.println("Book " + book);
                int res = dbConnectionMSSQL.updateBook(book);
                resp.getWriter().print(res > 0);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
