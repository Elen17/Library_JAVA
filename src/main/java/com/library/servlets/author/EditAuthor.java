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
        StringBuilder builder = new StringBuilder();
        Scanner in = new Scanner(req.getInputStream());
        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }
        try {
            Gson gson = new Gson();
            Author author = gson.fromJson(builder.toString(), Author.class);
            System.out.println("Author: " + author);
            int result = DBConnectionMSSQL.getInstance().updateAuthor(author);
            System.out.println(result + " query result");
            resp.getWriter().print(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
