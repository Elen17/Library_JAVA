package com.library.servlets.author;

import com.google.gson.Gson;
import com.library.author.Author;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class EditAuthor extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        StringBuilder builder = new StringBuilder();
        Scanner in = new Scanner(req.getInputStream());
        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }

        try {
//           System.out.println(builder.toString());
            Gson gson = new Gson();
            Author author = gson.fromJson(builder.toString(), Author.class);
            System.out.println("Author: " + author);

            resp.getWriter().write(DBConnectionMSSQL.getInstance().updateAuthor(author));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
