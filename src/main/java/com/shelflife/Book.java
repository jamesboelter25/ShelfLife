package com.shelflife;

// my Book class - it holds all the info for one book
// I made most of the fields Strings so it's easier for me to put them back in the form
public class Book {

    private int id;
    private String title = "";
    private String author = "";
    private String isbn = "";
    private String genre = "";
    private String format = "";
    private String publicationYear = "";
    private String pageCount = "";
    private String readingStatus = "want_to_read";
    private String rating = "";
    private String dateFinished = "";
    private boolean owned = true;
    private String notes = "";

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = fixNull(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = fixNull(author);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = fixNull(isbn);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = fixNull(genre);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = fixNull(format);
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = fixNull(publicationYear);
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = fixNull(pageCount);
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = fixNull(readingStatus);
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = fixNull(rating);
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(String dateFinished) {
        this.dateFinished = fixNull(dateFinished);
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = fixNull(notes);
    }

    // my database has "paperback" but I want it to say "Paperback" on my page
    public String getFormatLabel() {
        if (format.equals("hardcover")) {
            return "Hardcover";
        }
        if (format.equals("paperback")) {
            return "Paperback";
        }
        if (format.equals("ebook")) {
            return "eBook";
        }
        if (format.equals("audiobook")) {
            return "Audiobook";
        }
        return "";
    }

    // I did the same thing for the reading status
    public String getStatusLabel() {
        if (readingStatus.equals("want_to_read")) {
            return "Want to Read";
        }
        if (readingStatus.equals("reading")) {
            return "Currently Reading";
        }
        if (readingStatus.equals("finished")) {
            return "Finished";
        }
        return "";
    }

    // I made this to show stars for the rating, like 3 = ★★★☆☆
    public String getStars() {
        if (rating.equals("")) {
            return "Not rated";
        }
        int number = Integer.parseInt(rating);
        String stars = "";
        for (int i = 1; i <= 5; i++) {
            if (i <= number) {
                stars = stars + "★";
            } else {
                stars = stars + "☆";
            }
        }
        return stars;
    }

    // if something is null I make it "" instead so my page doesnt say "null"
    private String fixNull(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
