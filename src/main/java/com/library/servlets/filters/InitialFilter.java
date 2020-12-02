package com.library.servlets.filters;

import com.library.servlets.author.GetAuthor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class InitialFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String url = req.getRequestURL().toString();
        System.out.println(url);

        String table = req.getParameter("table");
        if (table != null && (table.equals("author") || table.equals("book"))) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            String query = req.getParameter("query");
            String newUrl = "/library/" + table;
            newUrl = query != null ? (newUrl + "/" + query) : newUrl;

            req.getRequestDispatcher(newUrl).forward(req, resp);
            return;
        }

        resp.sendRedirect("http://localhost:4200/library/errorPage");
    }

    @Override
    public void destroy() {

    }
}
