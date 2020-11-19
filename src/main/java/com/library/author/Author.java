package com.library.author;

import com.library.book.Book;
import com.library.db.DBConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Author extends Person implements Cloneable {
    private Map<Integer, Book> books;

    public Author(int id, String name, String surname, Date birthDate, Date deathDate, Address address) throws SQLException {
        super(id, name, surname, birthDate, deathDate, address);
        this.books  = completeBooks();
    }


    public void addBook(Book newBook) {
        books.put(newBook.getID(), newBook);
    }

    public boolean removeBook(int bookID) {
        return books.remove(bookID, books.get(bookID));
    }

    public List<Book> getBooks() throws SQLException {
        if(this.books == null){
            this.books = completeBooks();
        }
        List<Book> bookList = new ArrayList<>();
        books.values().forEach(book -> {
            try {
                bookList.add((Book) book.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        return bookList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        try {
            return Author.cloneInstance(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Author cloneInstance(Author author) throws SQLException {
        Map<Integer, Book> books = author.getBooks().stream().collect(Collectors.toMap(Book::getID, book -> book)); //todo:  book -> book
        //        newAuthor.setBooks(books);
        return new Author(author.getId(), author.getName(), author.getSurname(), author.getBirthDate(), author.getDeathDate(), author.getAddress());
    }

    private Map<Integer, Book> completeBooks() throws SQLException {
        ResultSet books = DBConnection.getInstance()
                .passQuery(String.format("select * from BOOK inner join BOOK_AUTHORS on BOOK.BOOK_ID = BOOK_AUTHORS.BOOK_ID" +
                        " where author_id = " +  this.getId()));
        Map<Integer, Book> result = new HashMap<>();
        if (books != null) {
            while (books.next()) {
                System.out.println(books.getString("BOOK_id"));
                result.put(books.getInt("book_id"), new Book(books.getInt("book_id"), books.getString("Title"), this.getFullName(),
                        books.getShort("page_count"), books.getString("country"), books.getShort("BOOK_year")));
            }
        }
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append(super.toString());
        sb.append("Books{\n");
        this.books.forEach((integer, book) -> sb.append(book));
        sb.append("}\n");
        sb.append('}');
        return sb.toString();
    }
}
