ShelfLife (A book collection app)
CIT345 Final Project - James Boelter
-----------------------------------

ShelfLife lets me keep track of the books I own and what I'm reading.
I can add books, search them, see the details, edit them and delete them.

Made with: Java Servlets, JSP, MySQL, Tomcat 10.1, HTML, CSS and a little JavaScript.


THE FILES
---------
pom.xml               - Maven file, gets the MySQL driver and builds the .war
Dockerfile            - only for putting it on Render
database/shelflife.sql - makes the database and the books table (+ 3 sample books)

src/main/java/com/shelflife/
  Book.java           - the Book class
  BookDAO.java        - all the SQL (add, get, update, delete)
  BookServlet.java    - the controller. also does the validation
  DBConnection.java   - connects to MySQL
  HtmlHelper.java     - escapes text so people can't put HTML in it

src/main/webapp/
  index.jsp           - just goes to the home page
  about.jsp           - about page
  error.jsp           - error / not found page
  css/style.css       - styles (the phone stuff is at the bottom)
  js/script.js        - the menu button for phones
  WEB-INF/web.xml     - maps the servlet to "books" and sets up the error pages
  WEB-INF/views/      - home, bookList, bookDetails, bookForm, header, footer

screenshots/          - pictures of it working on my computer, both in a desktop window and mobile window size to simulate running on a mobile device


HOW IT WORKS
------------
Everything goes through BookServlet using "books?action=...":

  books?action=home          home page with the counts
  books?action=list          my library + search
  books?action=new           add book form
  books?action=view&id=1     book details
  books?action=edit&id=1     edit form
  POST action=add            checks the form, then adds the book
  POST action=update         checks the form, then updates the book
  POST action=delete         deletes the book

Servlet -> BookDAO -> MySQL, then the servlet sends the data to a JSP page.
