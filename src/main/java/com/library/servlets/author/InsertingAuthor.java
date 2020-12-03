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

public class InsertingAuthor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        try {

            StringBuilder content = new StringBuilder();
            try (Scanner in = new Scanner(req.getInputStream())) {
                while (in.hasNext()) {
                    content.append(in.next());
                }
            }
            Gson gson = new Gson();

            System.out.println(gson.fromJson(content.toString(), Author.class));
            Author author = gson.fromJson(content.toString(), Author.class);
//            System.out.println("Converting to Author");
//            System.out.println(author.getAddress().getCountry());
//            boolean succeed = DBConnectionMySQL.getInstance().insertAuthor(author);
//            boolean succeed = DBConnectionMSSQL.getInstance().insertAuthor(author);
//            System.out.println(succeed);

            resp.getWriter().write(DBConnectionMSSQL.getInstance().insertAuthor(author)+"");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}