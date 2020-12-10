package com.library.servlets.book;

import com.google.gson.Gson;
import com.library.book.Book;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertBook extends HttpServlet {
    private Gson gson = new Gson();
    private final String GET_MAX_ID = "SELECT MAX(BOOK_ID) AS MAX FROM BOOK";
    private DBConnectionMSSQL db = DBConnectionMSSQL.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder content = new StringBuilder();
        try(Scanner in = new Scanner(req.getInputStream())){
            while (in.hasNextLine()){
                content.append(in.nextLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        Book book = gson.fromJson(content.toString(), Book.class);
        System.out.println("Book: " + book);
        try{
            int res = db.insertBook(book);
            System.out.println("result" + res);
            if(res > 0){
                ResultSet result = db.passQuery(GET_MAX_ID);
                while (result.next()){
                    res = result.getInt("MAX");
                }
            }
            System.out.println(res + " result");
            resp.getWriter().print(res);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
