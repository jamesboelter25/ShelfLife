package com.shelflife;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// my BookServlet - this is my controller
// I made all my links go to "books?action=something" and this decides what to do
//
// I use doGet  for showing pages (home, list, new, view, edit)
// I use doPost for saving stuff (add, update, delete)
public class BookServlet extends HttpServlet {

    BookDAO dao = new BookDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        //System.out.println("doGet action = " + action);

        try {

            // ===== HOME PAGE =====
            if (action.equals("home")) {
                ArrayList<Book> books = dao.getAllBooks();

                // I count how many books have each status
                int total = books.size();
                int reading = 0;
                int finished = 0;
                int wantToRead = 0;

                for (int i = 0; i < books.size(); i++) {
                    String status = books.get(i).getReadingStatus();
                    if (status.equals("reading")) {
                        reading = reading + 1;
                    } else if (status.equals("finished")) {
                        finished = finished + 1;
                    } else if (status.equals("want_to_read")) {
                        wantToRead = wantToRead + 1;
                    }
                }

                request.setAttribute("total", total);
                request.setAttribute("reading", reading);
                request.setAttribute("finished", finished);
                request.setAttribute("wantToRead", wantToRead);
                request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
            }

            // ===== ADD BOOK FORM =====
            else if (action.equals("new")) {
                Book book = new Book();   // I give it an empty book so my form is blank
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/bookForm.jsp").forward(request, response);
            }

            // ===== BOOK DETAILS =====
            else if (action.equals("view")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Book book = dao.getBook(id);

                if (book == null) {
                    response.sendError(404);   // this shows my error.jsp
                } else {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/WEB-INF/views/bookDetails.jsp").forward(request, response);
                }
            }

            // ===== EDIT FORM =====
            else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Book book = dao.getBook(id);

                if (book == null) {
                    response.sendError(404);
                } else {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/WEB-INF/views/bookForm.jsp").forward(request, response);
                }
            }

            // ===== MY LIBRARY (I made this the default) =====
            else {
                String search = request.getParameter("search");
                String genre = request.getParameter("genre");
                String status = request.getParameter("status");
                if (search == null) {
                    search = "";
                }
                if (genre == null) {
                    genre = "";
                }
                if (status == null) {
                    status = "";
                }

                ArrayList<Book> allBooks = dao.getAllBooks();
                ArrayList<Book> books = new ArrayList<Book>();

                // I go through all the books and only keep the ones that match
                for (int i = 0; i < allBooks.size(); i++) {
                    Book book = allBooks.get(i);
                    boolean keep = true;

                    // I search the title and author (I made them lowercase so caps don't matter)
                    if (!search.equals("")) {
                        String s = search.toLowerCase();
                        String title = book.getTitle().toLowerCase();
                        String author = book.getAuthor().toLowerCase();
                        if (!title.contains(s) && !author.contains(s)) {
                            keep = false;
                        }
                    }

                    if (!genre.equals("") && !book.getGenre().equals(genre)) {
                        keep = false;
                    }

                    if (!status.equals("") && !book.getReadingStatus().equals(status)) {
                        keep = false;
                    }

                    if (keep == true) {
                        books.add(book);
                    }
                }

                request.setAttribute("books", books);
                request.getRequestDispatcher("/WEB-INF/views/bookList.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // if something goes wrong (usually my database or a bad id) I show my error page
            e.printStackTrace();
            request.setAttribute("errorMessage", "Sorry, something went wrong. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");   // I need this so special characters save right

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        //System.out.println("doPost action = " + action);

        try {

            // ===== DELETE =====
            if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteBook(id);
                response.sendRedirect("books?action=list&message=deleted");
                return;
            }

            // ===== ADD or UPDATE =====
            // I get everything from my form and put it in a Book
            Book book = new Book();
            book.setTitle(request.getParameter("title"));
            book.setAuthor(request.getParameter("author"));
            book.setIsbn(request.getParameter("isbn"));
            book.setGenre(request.getParameter("genre"));
            book.setFormat(request.getParameter("format"));
            book.setPublicationYear(request.getParameter("publicationYear"));
            book.setPageCount(request.getParameter("pageCount"));
            book.setReadingStatus(request.getParameter("readingStatus"));
            book.setRating(request.getParameter("rating"));
            book.setDateFinished(request.getParameter("dateFinished"));
            book.setNotes(request.getParameter("notes"));

            // my checkbox is null if it's not checked
            if (request.getParameter("owned") == null) {
                book.setOwned(false);
            } else {
                book.setOwned(true);
            }

            // when I'm editing, my form sends the id too
            if (action.equals("update")) {
                book.setId(Integer.parseInt(request.getParameter("id")));
            }

            // I take the dashes and spaces out of the isbn
            String isbn = book.getIsbn();
            isbn = isbn.replace("-", "");
            isbn = isbn.replace(" ", "");
            isbn = isbn.toUpperCase();
            book.setIsbn(isbn);


            // ========== VALIDATION ==========
            // if anything is wrong I add a message to this list
            ArrayList<String> errors = new ArrayList<String>();

            // TITLE - required, max 255
            if (book.getTitle().equals("")) {
                errors.add("Title is required.");
            } else if (book.getTitle().length() > 255) {
                errors.add("Title can't be more than 255 characters.");
            }

            // AUTHOR - required, 2 to 150 characters, only letters and . - ' and spaces
            String author = book.getAuthor();
            if (author.equals("")) {
                errors.add("Author is required.");
            } else if (author.length() < 2 || author.length() > 150) {
                errors.add("Author must be between 2 and 150 characters.");
            } else {
                boolean authorOk = true;
                for (int i = 0; i < author.length(); i++) {
                    char c = author.charAt(i);
                    if (!Character.isLetter(c) && c != ' ' && c != '.' && c != '-' && c != '\'') {
                        authorOk = false;
                    }
                }
                if (authorOk == false) {
                    errors.add("Author can only have letters, spaces, periods, hyphens and apostrophes.");
                }
            }

            // ISBN - not required, but if I type one it has to be 10 or 13 digits
            // (I found out a 10 digit isbn can end with X)
            if (!isbn.equals("")) {
                boolean isbnOk = true;

                if (isbn.length() != 10 && isbn.length() != 13) {
                    isbnOk = false;
                }

                for (int i = 0; i < isbn.length(); i++) {
                    char c = isbn.charAt(i);
                    if (!Character.isDigit(c)) {
                        boolean xAtEnd = (c == 'X' && i == 9 && isbn.length() == 10);
                        if (xAtEnd == false) {
                            isbnOk = false;
                        }
                    }
                }

                if (isbnOk == false) {
                    errors.add("ISBN must be 10 or 13 digits.");
                } else if (dao.isbnExists(isbn, book.getId())) {
                    errors.add("That ISBN is already in your library.");
                }
            }

            // GENRE - has to be one of the ones in my dropdown
            String[] genres = {"Fiction", "Mystery/Thriller", "Science Fiction", "Fantasy", "Romance",
                    "Historical Fiction", "Non-Fiction", "Biography/Memoir", "Self-Help",
                    "History", "Science", "Graphic Novel", "Poetry", "Other"};
            boolean genreOk = false;
            for (int i = 0; i < genres.length; i++) {
                if (genres[i].equals(book.getGenre())) {
                    genreOk = true;
                }
            }
            if (genreOk == false) {
                errors.add("Please pick a genre.");
            }

            // FORMAT - one of my 4 radio buttons
            String format = book.getFormat();
            if (!format.equals("hardcover") && !format.equals("paperback") && !format.equals("ebook") && !format.equals("audiobook")) {
                errors.add("Please pick a format.");
            }

            // READING STATUS
            String status = book.getReadingStatus();
            if (!status.equals("want_to_read") && !status.equals("reading") && !status.equals("finished")) {
                errors.add("Please pick a reading status.");
            }

            // PUBLICATION YEAR - not required, has to be 1450 to this year
            // (I picked 1450 because that's around when the printing press was invented)
            int thisYear = LocalDate.now().getYear();
            if (!book.getPublicationYear().equals("")) {
                try {
                    int year = Integer.parseInt(book.getPublicationYear());
                    if (year < 1450 || year > thisYear) {
                        errors.add("Publication year must be between 1450 and " + thisYear + ".");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Publication year must be a number.");
                }
            }

            // PAGE COUNT - not required, 1 to 10000
            if (!book.getPageCount().equals("")) {
                try {
                    int pages = Integer.parseInt(book.getPageCount());
                    if (pages < 1 || pages > 10000) {
                        errors.add("Page count must be between 1 and 10,000.");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Page count must be a whole number.");
                }
            }

            // RATING - not required, 1 to 5
            if (!book.getRating().equals("")) {
                try {
                    int rating = Integer.parseInt(book.getRating());
                    if (rating < 1 || rating > 5) {
                        errors.add("Rating must be 1 to 5 stars.");
                    }
                } catch (NumberFormatException e) {
                    errors.add("Rating must be 1 to 5 stars.");
                }
            }

            // DATE FINISHED - required if the status is Finished
            if (status.equals("finished") && book.getDateFinished().equals("")) {
                errors.add("Date finished is required when the status is Finished.");
            }
            // and I don't let it be in the future
            if (!book.getDateFinished().equals("")) {
                try {
                    LocalDate date = LocalDate.parse(book.getDateFinished());
                    if (date.isAfter(LocalDate.now())) {
                        errors.add("Date finished can't be in the future.");
                    }
                } catch (Exception e) {
                    errors.add("Date finished is not a valid date.");
                }
            }

            // NOTES - max 1000
            if (book.getNotes().length() > 1000) {
                errors.add("Notes can't be more than 1,000 characters.");
            }
            // ========== END OF VALIDATION ==========


            // if there are errors I go back to my form (it keeps what was typed)
            if (errors.size() > 0) {
                request.setAttribute("errors", errors);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/bookForm.jsp").forward(request, response);
                return;
            }

            // no errors so I save it!
            if (action.equals("add")) {
                dao.addBook(book);
                response.sendRedirect("books?action=list&message=added");
            } else if (action.equals("update")) {
                dao.updateBook(book);
                response.sendRedirect("books?action=view&id=" + book.getId() + "&message=updated");
            } else {
                response.sendRedirect("books");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Sorry, something went wrong while saving. Please try again.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
