package com.library.book;

import com.library.author.Author;

public final class Book implements Cloneable {
    private final int bookID;
    private final String title;
    private final String authorName;
    private final short pageCount;
    private final String country;
    private final short publishedYear;

    public Book(int bookID, String title, String authorName, short pageCount, String country, short publishedYear) {
        this.bookID = bookID;
        this.title = title;
        this.authorName = authorName;
        this.pageCount = pageCount;
        this.country = country;
        this.publishedYear = publishedYear;
    }

    public int getID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {

        return this.getAuthor();
    }

    public short getPageCount() {
        return pageCount;
    }

    public String getCountry() {
        return country;
    }

    public short getPublishedYear() {
        return publishedYear;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return getClone(this);
    }

    private static Book getClone(Book current) {
        return new Book(current.getID(), current.getTitle(), current.getAuthor(), current.getPageCount(), current.getCountry(), current.getPublishedYear());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("bookID=").append(bookID);
        sb.append(", title='").append(title).append('\'');
//        sb.append(", author=").append(author);
        sb.append(", pageCount=").append(pageCount);
        sb.append(", country='").append(country).append('\'');
        sb.append(", publishedYear=").append(publishedYear);
        sb.append('}');
        return sb.toString();
    }
}
