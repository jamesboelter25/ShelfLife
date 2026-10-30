package com.shelflife;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

// my BookDAO - I put all my database stuff for the books table in here
// I'm using PreparedStatement with the ? marks so nobody can do SQL injection
public class BookDAO {

    // I get every book here (sorted by title)
    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<Book>();

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM books ORDER BY title");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setIsbn(rs.getString("isbn"));
            book.setGenre(rs.getString("genre"));
            book.setFormat(rs.getString("format"));
            book.setPublicationYear(rs.getString("publication_year"));
            book.setPageCount(rs.getString("page_count"));
            book.setReadingStatus(rs.getString("reading_status"));
            book.setRating(rs.getString("rating"));
            book.setDateFinished(rs.getString("date_finished"));
            book.setOwned(rs.getBoolean("owned"));
            book.setNotes(rs.getString("notes"));
            books.add(book);
        }

        rs.close();
        ps.close();
        conn.close();
        return books;
    }

    // I get just one book here. it returns null if there is no book with that id
    public Book getBook(int id) throws SQLException {
        Book book = null;

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setIsbn(rs.getString("isbn"));
            book.setGenre(rs.getString("genre"));
            book.setFormat(rs.getString("format"));
            book.setPublicationYear(rs.getString("publication_year"));
            book.setPageCount(rs.getString("page_count"));
            book.setReadingStatus(rs.getString("reading_status"));
            book.setRating(rs.getString("rating"));
            book.setDateFinished(rs.getString("date_finished"));
            book.setOwned(rs.getBoolean("owned"));
            book.setNotes(rs.getString("notes"));
        }

        rs.close();
        ps.close();
        conn.close();
        return book;
    }

    // I add a new book here
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, genre, format, publication_year, page_count, reading_status, rating, date_finished, owned, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());

        // I have to save the empty ones as NULL in my database
        if (book.getIsbn().equals("")) {
            ps.setNull(3, Types.VARCHAR);
        } else {
            ps.setString(3, book.getIsbn());
        }

        ps.setString(4, book.getGenre());
        ps.setString(5, book.getFormat());

        if (book.getPublicationYear().equals("")) {
            ps.setNull(6, Types.INTEGER);
        } else {
            ps.setInt(6, Integer.parseInt(book.getPublicationYear()));
        }

        if (book.getPageCount().equals("")) {
            ps.setNull(7, Types.INTEGER);
        } else {
            ps.setInt(7, Integer.parseInt(book.getPageCount()));
        }

        ps.setString(8, book.getReadingStatus());

        if (book.getRating().equals("")) {
            ps.setNull(9, Types.INTEGER);
        } else {
            ps.setInt(9, Integer.parseInt(book.getRating()));
        }

        if (book.getDateFinished().equals("")) {
            ps.setNull(10, Types.DATE);
        } else {
            ps.setDate(10, java.sql.Date.valueOf(book.getDateFinished()));
        }

        ps.setBoolean(11, book.isOwned());
        ps.setString(12, book.getNotes());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // I update a book here (pretty much the same as my addBook but with UPDATE)
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, genre = ?, format = ?, publication_year = ?, page_count = ?, reading_status = ?, rating = ?, date_finished = ?, owned = ?, notes = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());

        if (book.getIsbn().equals("")) {
            ps.setNull(3, Types.VARCHAR);
        } else {
            ps.setString(3, book.getIsbn());
        }

        ps.setString(4, book.getGenre());
        ps.setString(5, book.getFormat());

        if (book.getPublicationYear().equals("")) {
            ps.setNull(6, Types.INTEGER);
        } else {
            ps.setInt(6, Integer.parseInt(book.getPublicationYear()));
        }

        if (book.getPageCount().equals("")) {
            ps.setNull(7, Types.INTEGER);
        } else {
            ps.setInt(7, Integer.parseInt(book.getPageCount()));
        }

        ps.setString(8, book.getReadingStatus());

        if (book.getRating().equals("")) {
            ps.setNull(9, Types.INTEGER);
        } else {
            ps.setInt(9, Integer.parseInt(book.getRating()));
        }

        if (book.getDateFinished().equals("")) {
            ps.setNull(10, Types.DATE);
        } else {
            ps.setDate(10, java.sql.Date.valueOf(book.getDateFinished()));
        }

        ps.setBoolean(11, book.isOwned());
        ps.setString(12, book.getNotes());
        ps.setInt(13, book.getId());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // I delete a book here
    public void deleteBook(int id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // I check if a different book already has this isbn
    // (id is the book I'm editing so it doesnt find itself. for a new book I use 0)
    public boolean isbnExists(String isbn, int id) throws SQLException {
        boolean found = false;

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE isbn = ? AND id != ?");
        ps.setString(1, isbn);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            found = true;
        }

        rs.close();
        ps.close();
        conn.close();
        return found;
    }
}
