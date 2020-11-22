package com.library.db;

import com.library.author.Address;
import com.library.author.Author;
import com.library.book.Book;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.mysql.jdbc.Driver;

public final class DBConnectionMSSQL {
    private static Connection connection;
    private static final Path path = Paths.get("connection.properties");
    private static String url;
    private static String userName;
    private static String password;
    private static DBConnectionMSSQL instance = null;
    private static PreparedStatement getAuthorByID;
    private static PreparedStatement getBookByID;
    private static PreparedStatement getAuthorByName;
    private static PreparedStatement getBookByTitle;
    private static PreparedStatement insertAuthor;
    private static PreparedStatement deleteAuthor;
    private static final String GET_AUTHOR = "select AUTHOR_ID as id, NAME, SURNAME, BIRTH_DATE as bd, DEATH_YEAR as dy, BIRTH_COUNTRY as coutry, BIRTH_CITY as city\n" +
            "from AUTHOR where author_id = ? ";

    private static final String GET_BOOK = "select B.BOOK_ID as id, B.TITLE as title, a.NAME as name, a.SURNAME as sname, b.PAGE_COUNT as page, b.COUNTRY as country, b.BOOK_YEAR as year " +
            "from BOOK as B " +
            "         inner join BOOK_AUTHORS as BA on B.BOOK_ID = BA.BOOK_ID" +
            "         inner join AUTHOR A on BA.AUTHOR_ID = A.AUTHOR_ID " +
            "where B.BOOK_ID = ? ";

    private static final String GET_AUTHOR_NAME = "select AUTHOR_ID as id, NAME, SURNAME, BIRTH_DATE as bd, DEATH_YEAR as dy, BIRTH_COUNTRY as country, BIRTH_CITY as city " +
            " from AUTHOR where [name] = ?";
    private static final String GET_BOOK_TITLE = "select B.BOOK_ID as id, B.TITLE as title, a.NAME as name, a.SURNAME as sname, b.PAGE_COUNT as page, b.COUNTRY as country, b.BOOK_YEAR as year FROM BOOK WHERE TITLE = ? ";

    private static final String INSERT_AUTHOR = "INSERT INTO AUTHOR (NAME, SURNAME, BIRTH_DATE, DEATH_YEAR, BIRTH_COUNTRY, BIRTH_CITY) VALUES ( ?, ?, ?, ?, ?, ? )";

    private static final String DELETE_AUTHOR = "DELETE FROM AUTHOR WHERE AUTHOR_ID = ? ";

    public static DBConnectionMSSQL getInstance() {

        if (instance == null) {
            synchronized (DBConnectionMSSQL.class) {
                if (instance == null) {
                    instance = new DBConnectionMSSQL();
                }
            }
        }
        return instance;
    }

    static {
        connect();
    }

    private static void setConnectionUrl() {
        try (InputStream in = new FileInputStream("C:\\Users\\elen.khachatryan\\IdeaProjects\\Library\\src\\main\\resources\\connection.properties")) {
//            System.out.println("Inside properties.");
            SQLServerDriver.register();
            Properties prop = new Properties();
            prop.load(in);
            url = prop.getProperty("url");
            userName = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connect() {
        setConnectionUrl();
        try {
            connection = DriverManager.getConnection(url, userName, password);

//            getAuthorByID = connection.prepareStatement(GET_AUTHOR);
//            getAuthorByName = connection.prepareStatement(GET_AUTHOR_NAME);
//            getBookByID = connection.prepareStatement(GET_BOOK);
//            getBookByTitle = connection.prepareStatement(GET_BOOK_TITLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet passQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public Author getAuthor(int id) throws SQLException {
        getAuthorByID = createPrepStatement(getAuthorByID, GET_AUTHOR);
        getAuthorByID.setInt(1, id);
        ResultSet result = getAuthorByID.executeQuery();
        if (result.next()) {
            LocalDate birthDate = result.getDate("bd").toLocalDate();
            Date deathDate = result.getDate("dy");
            System.out.println("Result:  " + result);
            return new Author(result.getInt("id"), result.getString("name"), result.getString("surname"),
                    birthDate, deathDate != null ? deathDate.toLocalDate() : null,
                    result.getString("country"), result.getString("city"));

        }
        return null;
    }

    public Map<Integer, Author> getAuthorsByName(String name) throws SQLException {
        getAuthorByName = createPrepStatement(getAuthorByName, GET_AUTHOR_NAME);
        getAuthorByName.setString(1, name);


        ResultSet result = getAuthorByName.executeQuery();
        Map<Integer, Author> authors = new HashMap<>();
        while (result.next()) {
            LocalDate birthDate = result.getDate("bd").toLocalDate();
            Date deathDate = result.getDate("dy");
            System.out.println("Result:  " + result);
            int id = result.getInt("id");
            authors.put(id, new Author(id, result.getString("name"), result.getString("surname"),
                    birthDate, deathDate != null ? deathDate.toLocalDate() : null,
                    result.getString("country"), result.getString("city")));

        }
        return authors;
    }

    public Book getBookById(int id) throws SQLException {
        getBookByID = createPrepStatement(getBookByID, GET_BOOK);
        getBookByID.setInt(1, id);
        ResultSet result = getBookByID.executeQuery();
        if (result.next()) {

            return new Book(result.getInt("id"), result.getString("title"),
                    result.getString("name") + " " + result.getString("sname"),
                    result.getShort("page"), result.getString("country"),
                    result.getShort("year"));
        }
        return null;
    }

    public Map<Integer, Book> getBooksByTitle(String title) throws SQLException {
        Map<Integer, Book> books = new HashMap<>();
        getBookByTitle = createPrepStatement(getBookByTitle, GET_BOOK_TITLE);
        getBookByTitle.setString(1, title);
        ResultSet result = getBookByID.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            books.put(id, new Book(id, result.getString("title"),
                    result.getString("name") + " " + result.getString("sname"),
                    result.getShort("page"), result.getString("country"),
                    result.getShort("year")));
        }
        return books;

    }

    public boolean insertAuthor(Author author) throws SQLException {
        insertAuthor = createPrepStatement(insertAuthor, INSERT_AUTHOR);
        insertAuthor.setString(1, author.getName());
        insertAuthor.setString(2, author.getSurname());
        insertAuthor.setDate(3, Date.valueOf(author.getBirthDate()));
        insertAuthor.setDate(4, author.getDeathDate() == null ? null : Date.valueOf(author.getDeathDate()));
        insertAuthor.setString(5, author.getAddress().getCountry());
        insertAuthor.setString(6, author.getAddress().getCity());

        // TODO: 11/20/2020 add cascade delete
        return insertAuthor.executeUpdate() == 1;
    }

    public boolean deleteAuthor(int id) throws SQLException {
        deleteAuthor = createPrepStatement(deleteAuthor, DELETE_AUTHOR);
        deleteAuthor.setInt(1, id);
        return deleteAuthor.executeUpdate() == 1;
    }

    private PreparedStatement createPrepStatement(PreparedStatement statement, String sql) throws SQLException {
        return statement == null ? connection.prepareStatement(sql) : statement;
    }

}