package com.library.servlets.author;

import com.google.gson.Gson;
import com.library.author.Author;
import com.library.db.DBConnectionMSSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertingAuthor extends HttpServlet {
    DBConnectionMSSQL db = DBConnectionMSSQL.getInstance();
    private final String GET_LAST_ID = "SELECT MAX(AUTHOR_ID) AS MAX FROM AUTHOR";
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
            System.out.println(content.toString());
            System.out.println(gson.fromJson(content.toString(), Author.class));
            Author author = gson.fromJson(content.toString(), Author.class);
            int res = db.insertAuthor(author);
            if(res > 0){
                ResultSet result = db.passQuery(GET_LAST_ID);
                while (result.next()){
                    res = result.getInt("MAX");
                }
            }
            resp.getWriter().print(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
