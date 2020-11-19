package com.library.servlets;

import com.google.gson.Gson;
import com.library.author.Address;
import com.library.author.Author;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class InsertingAuthor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            StringBuilder content = new StringBuilder();
            try (Scanner in = new Scanner(req.getInputStream())) {
                while (in.hasNext()) {
                    content.append(in.next());
                }
            }
            Gson gson = new Gson();
            System.out.println(gson.toJson(new Author(1, "name", "sname", Date.valueOf(LocalDate.now().minusDays(1000)), Date.valueOf(LocalDate.now()),
                    new Address("country", "city"))).toString());

            System.out.println(content.toString());
            Author aut = gson.fromJson(content.toString(), Author.class);
//        String parameter = req.getParameter("author");
            System.out.println(aut + " resss");
            System.out.println(aut.getAge());
//        System.out.println(parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getQueryString());
        System.out.println(req.getRequestURI());
        resp.getWriter().write("wirte");
//        String parameter = req.getParameter("author");
//        System.out.println(parameter);
    }
}
