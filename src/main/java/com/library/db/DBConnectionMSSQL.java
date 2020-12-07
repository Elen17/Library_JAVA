package com.library.db;

import com.library.author.Address;
import com.library.author.Author;
import com.library.book.Book;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DBConnectionMSSQL {
    private static Connection connection;
    private static final Path path = Paths.get("connection.properties");
    private static String url;
    private static String userName;
    private static String password;
    private static DBConnectionMSSQL instance = null;
    private static PreparedStatement getAuthorByID;
    private static PreparedStatement getAuthorByName;
    private static PreparedStatement insertAuthor;
    private static PreparedStatement deleteAuthor;
    private static PreparedStatement updateAuthor;

    private static PreparedStatement getAllBooks;
    private static PreparedStatement getBookByID;
    private static PreparedStatement getBookByTitle;
    private static PreparedStatement getBookByAuthor;
    private static PreparedStatement updateBook;
    private static PreparedStatement deleteBook;
    private static PreparedStatement insertBook;


    private static final String UPDATE_AUTHOR = "UPDATE AUTHOR " +
            "SET NAME = ?, SURNAME = ? ,  BIRTH_DATE = ?, " +
            "DEATH_YEAR = ?, " +
            "BIRTH_COUNTRY = ?, " +
            "BIRTH_CITY = ?  " +
            "WHERE AUTHOR_ID = ?;";

    private static final String GET_ALL_AUTHORS = "SELECT AUTHOR_ID as ID, " +
            "NAME, " +
            "SURNAME, " +
            "BIRTH_DATE AS BIRTH, " +
            "DEATH_YEAR AS DEATH, " +
            "BIRTH_COUNTRY AS COUNTRY, " +
            "BIRTH_CITY AS CITY FROM AUTHOR ";

    private static final String GET_AUTHOR = "SELECT AUTHOR_ID as id, NAME, SURNAME, BIRTH_DATE as bd, DEATH_YEAR as dy, BIRTH_COUNTRY as coutry, BIRTH_CITY as city " +
            "FROM AUTHOR WHERE author_id = ? ";

    private static final String GET_AUTHOR_BY_NAME = "SELECT AUTHOR_ID as id, NAME, SURNAME, BIRTH_DATE as bd, DEATH_YEAR as dy, BIRTH_COUNTRY as country, BIRTH_CITY as city " +
            " FROM AUTHOR WHERE [name] = ? AND [surname] = ?";

    private static final String INSERT_AUTHOR = "INSERT INTO AUTHOR (NAME, SURNAME, BIRTH_DATE, DEATH_YEAR, BIRTH_COUNTRY, BIRTH_CITY) VALUES ( ?, ?, ?, ?, ?, ? )";

    private static final String DELETE_AUTHOR = " DELETE FROM AUTHOR WHERE AUTHOR_ID = ? ";

    private static final String GET_AUTHORS_NAMES = "SELECT AUTHOR_ID as id,  (NAME + ' ' + SURNAME) AS FULLNAME FROM AUTHOR";


    private static final String GET_ALL_BOOKS = "SELECT B.BOOK_ID AS ID, B.TITLE AS TITLE, B.BOOK_YEAR AS year, B.COUNTRY , B.page_count, (A.NAME + ' ' + A.SURNAME) AS NAME, B.AUTHOR_ID AS AUTHOR" +
            " FROM BOOK as B " +
            "         INNER JOIN AUTHOR A ON B.AUTHOR_ID = A.AUTHOR_ID";


    private static final String GET_BOOK = "SELECT B.BOOK_ID AS id," +
            "       B.TITLE                    AS title," +
            "       (A.NAME + ' ' + A.SURNAME) AS NAME," +
            "       B.PAGE_COUNT               AS page," +
            "       B.COUNTRY                  AS country," +
            "       B.BOOK_YEAR                AS year" +
            "       B.AUTHOR_ID                AS AUTHOR " +
            " FROM BOOK AS B" +
            "         INNER JOIN AUTHOR A ON B.AUTHOR_ID = A.AUTHOR_ID " +
            " WHERE B.BOOK_ID = ?";


    private static final String GET_BOOK_BY_TITLE = "SELECT " +
            "       B.BOOK_ID                  AS id," +
            "       B.TITLE                    AS title," +
            "       (a.NAME + ' ' + a.SURNAME) AS NAME," +
            "       b.PAGE_COUNT               AS page," +
            "       b.COUNTRY                  AS country," +
            "       b.BOOK_YEAR                AS year," +
            "       b.AUTHOR_ID                AS AUTHOR" +
            "   FROM BOOK AS B" +
            "         INNER JOIN AUTHOR AS A ON B.AUTHOR_ID = A.AUTHOR_ID" +
            "   WHERE TITLE = ?";

    private static final String GET_BOOKS_BY_AUTHOR = "SELECT B.BOOK_ID  AS id, " +
            "       B.TITLE                    AS title, " +
            "       (a.NAME + ' ' + a.SURNAME) AS NAME, " +
            "       b.PAGE_COUNT               AS page, " +
            "       b.COUNTRY                  AS country, " +
            "       b.BOOK_YEAR                AS year" +
            "FROM BOOK AS B " +
            "         INNER JOIN AUTHOR A ON B.AUTHOR_ID = A.AUTHOR_ID " +
            "WHERE B.AUTHOR_ID = ?";
    private static final String UPDATE_BOOK = "UPDATE BOOK " +
            "SET TITLE = ?, " +
            "    BOOK_YEAR = ? , " +
            "    BOOK_INFO = ? , " +
            "    COUNTRY = ? , " +
            "    page_count = ? " +
            "    AUTHOR_ID = ?  " +
            "WHERE BOOK_ID = ?; ";

    private static final String DELETE_BOOK = "DELETE FROM BOOK WHERE BOOK_ID = ?";
    private static final String INSERT_BOOK = "INSERT INTO BOOK(TITLE, PAGE_COUNT, COUNTRY, YEAR, AUTHOR_ID) " +
            "VALUES(?, ?, ?, ?, ?)";

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
        try {
//        (InputStream in = new FileInputStream("src\\main\\resources\\connection.properties")) {
//            ies.");
            SQLServerDriver.register();
//            Properties prop = new Properties();
//            prop.load(in);
//            url = prop.getProperty("url");
//            userName = prop.getProperty("user");
//            password = prop.getProperty("password");
            url = "jdbc:sqlserver://localhost:1433;databaseName=Library";
            userName = "lib_login";
            password = "password";
//
        } catch (SQLException e) {
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
            return new Author(result.getInt("id"), result.getString("name"), result.getString("surname"),
                    birthDate, deathDate != null ? deathDate.toLocalDate() : null,
                    new Address(result.getString("country"), result.getString("city")));

        }
        return null;
    }

    public Map<Integer, Author> getAuthorsByFullName(String name, String surname) throws SQLException {
        getAuthorByName = createPrepStatement(getAuthorByName, GET_AUTHOR_BY_NAME);

        getAuthorByName.setString(1, name);
        getAuthorByName.setString(2, surname);


        ResultSet result = getAuthorByName.executeQuery();
        Map<Integer, Author> authors = new HashMap<>();
        while (result.next()) {
            LocalDate birthDate = result.getDate("bd").toLocalDate();
            Date deathDate = result.getDate("dy");
            int id = result.getInt("id");
            authors.put(id, new Author(id, result.getNString("name"), result.getNString("surname"),
                    birthDate, deathDate != null ? deathDate.toLocalDate() : null,
                    new Address(result.getString("country"), result.getString("city"))));

        }

        return authors;
    }

    public boolean insertAuthor(Author author) throws SQLException {
        insertAuthor = createPrepStatement(insertAuthor, INSERT_AUTHOR);
        insertAuthor.setNString(1, author.getName());
        insertAuthor.setNString(2, author.getSurname());
        insertAuthor.setDate(3, Date.valueOf(author.getBirthDate()));
        insertAuthor.setDate(4, author.getDeathDate() == null ? null : Date.valueOf(author.getDeathDate()));
        insertAuthor.setString(5, author.getAddress().getCountry());
        insertAuthor.setString(6, author.getAddress().getCity());

        return insertAuthor.executeUpdate() == 1;
    }


    public int updateAuthor(Author author) throws SQLException {
        updateAuthor = createPrepStatement(updateAuthor, UPDATE_AUTHOR);
        updateAuthor.setNString(1, author.getName());//name
        updateAuthor.setNString(2, author.getSurname());//name
        updateAuthor.setDate(3, Date.valueOf(author.getBirthDate()));//name
        updateAuthor.setDate(4, author.getDeathDate() != null ? Date.valueOf(author.getDeathDate()) : null);//name
        updateAuthor.setNString(5, author.getAddress().getCountry());//name
        updateAuthor.setNString(6, author.getAddress().getCity());//name
        updateAuthor.setInt(7, author.getId());//name
        return updateAuthor.executeUpdate();
    }

    public boolean deleteAuthor(int id) throws SQLException {
        deleteAuthor = createPrepStatement(deleteAuthor, DELETE_AUTHOR);
        deleteAuthor.setInt(1, id);
        deleteAuthor.setInt(2, id);
        return deleteAuthor.executeUpdate() == 1;
    }

    public Map<Integer, String> getAuthorsNames() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(GET_AUTHORS_NAMES);
        Map<Integer, String> names = new HashMap<>();
        while (result.next()) {
            names.put(result.getInt(1), result.getString("FULLNAME"));
        }
        return names;

    }

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> result = new ArrayList<>();
        Statement getAuthors = connection.createStatement();
        ResultSet authors = getAuthors.executeQuery(GET_ALL_AUTHORS);
        while (authors.next()) {
            result.add(new Author(authors.getInt("ID"), authors.getString("name"),
                    authors.getString("surname"), authors.getDate("birth").toLocalDate(),
                    (authors.getDate("death") != null ? authors.getDate("death").toLocalDate() : null),
                    new Address(authors.getString("country"), authors.getString("city"))));
        }
        return result;
    }

    public Map<Integer, Book> getAllBooks() throws SQLException {
        Map<Integer, Book> result = new HashMap<>();
        final Statement getAllBooks = connection.createStatement();
        ResultSet books = getAllBooks.executeQuery(GET_ALL_BOOKS);
        while (books.next()) {
            int id = books.getInt("ID");
            result.put(id, new Book(id, books.getString("title"),
                    books.getInt("AUTHOR"),
                    books.getNString("name"),
                    (short) books.getInt("page_count"),
                    books.getString("country"),
                    (short) books.getInt("year")));
        }
        System.out.println("Books" + " " + result);
        return result;
    }


    public Book getBookById(int id) throws SQLException {
        getBookByID = createPrepStatement(getBookByID, GET_BOOK);
        getBookByID.setInt(1, id);
        ResultSet result = getBookByID.executeQuery();
        if (result.next()) {

            return new Book(result.getInt("id"), result.getString("title"),
                    result.getInt("AUTHOR"), result.getString("name"),
                    result.getShort("page"), result.getString("country"),
                    result.getShort("year"));
        }
        return null;
    }

    public Map<Integer, Book> getBooksByTitle(String title) throws SQLException {
        Map<Integer, Book> books = new HashMap<>();
        getBookByTitle = createPrepStatement(getBookByTitle, GET_BOOK_BY_TITLE);
        getBookByTitle.setString(1, title);
        ResultSet result = getBookByTitle.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            books.put(id, new Book(id, result.getString("title"),
                    result.getInt("author"), result.getString("name"),
                    result.getShort("page"), result.getString("country"),
                    result.getShort("year")));
        }
        return books;
    }

    public Map<Integer, Book> getBooksByAuthor(int authorID) throws SQLException {
        Map<Integer, Book> books = new HashMap<>();
        getBookByAuthor = createPrepStatement(getBookByAuthor, GET_BOOKS_BY_AUTHOR);
        getBookByAuthor.setInt(1, authorID);
        ResultSet result = getBookByAuthor.executeQuery();
        while (result.next()) {
            int id = result.getInt("id");
            books.put(id, new Book(id, result.getString("title"), result.getInt("author"),
                    result.getNString("name"), (short) result.getInt("page_count"), result.getString("country"),
                    (short) result.getInt("year")));
        }
        return books;
    }

    private PreparedStatement createPrepStatement(PreparedStatement statement, String sql) throws SQLException {
        return statement == null ? connection.prepareStatement(sql) : statement;
    }


}
