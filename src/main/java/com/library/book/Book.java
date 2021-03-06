package com.library.book;

public final class Book implements Cloneable {
    private final int bookID;
    private final String title;
    private final int authorID;
    private final short pageCount;
    private final String country;
    private final short year;
    private final String authorName;

    public Book(int bookID, String title, int authorID, String authorName, short pageCount, String country, short publishedYear) {
        this.bookID = bookID;
        this.title = title;
        this.authorID = authorID;
        this.pageCount = pageCount;
        this.country = country;
        this.year = publishedYear;
        this.authorName = authorName;
    }

    public int getID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public short getPageCount() {
        return pageCount;
    }

    public String getCountry() {
        return country;
    }

    public short getYear() {
        return year;
    }

    public int getAuthorID() {
        return authorID;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return getClone(this);
    }

    private static Book getClone(Book current) {
        return new Book(current.getID(), current.getTitle(), current.getAuthorID(), current.getAuthorName(), current.getPageCount(), current.getCountry(), current.getYear());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("bookID=").append(bookID);
        sb.append(", title='").append(title).append('\'');
        sb.append(", authorID=").append(authorID);
        sb.append(", authorName=").append(authorName);
        sb.append(", pageCount=").append(pageCount);
        sb.append(", country='").append(country).append('\'');
        sb.append(", year=").append(year);
        sb.append('}');
        return sb.toString();
    }

    public String getAuthorName() {
        return authorName;
    }
}
