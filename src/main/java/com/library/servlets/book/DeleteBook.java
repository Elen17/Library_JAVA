package com.library.servlets.book;

import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteBook extends HttpServlet {
    private DBConnectionMSSQL connection = DBConnectionMSSQL.getInstance();
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookID = req.getParameter("param");
        try{
            resp.getWriter().print(connection.deleteBook(Integer.parseInt(bookID))  > 0);
        }catch (SQLException | NumberFormatException e){
            e.printStackTrace();
        }
    }
}
