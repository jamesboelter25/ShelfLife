<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.shelflife.Book, com.shelflife.HtmlHelper, java.util.ArrayList" %>
<%
    // I used the same form for Add and Edit so I don't have to make it twice
    Book book = (Book) request.getAttribute("book");
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");

    // new books have an id of 0, so if the id is bigger than 0 I'm editing
    boolean editing = false;
    if (book.getId() > 0) {
        editing = true;
    }

    String pageTitle = "Add Book";
    if (editing == true) {
        pageTitle = "Edit Book";
    }

    String[] genres = {"Fiction", "Mystery/Thriller", "Science Fiction", "Fantasy", "Romance",
            "Historical Fiction", "Non-Fiction", "Biography/Memoir", "Self-Help",
            "History", "Science", "Graphic Novel", "Poetry", "Other"};

    int thisYear = java.time.LocalDate.now().getYear();
%>
<%@ include file="header.jsp" %>

<h1><%= pageTitle %></h1>

<%-- I show the errors from my servlet if there are any --%>
<% if (errors != null && errors.size() > 0) { %>
    <div class="error-box">
        <strong>Please fix these problems:</strong>
        <ul>
            <% for (int i = 0; i < errors.size(); i++) { %>
                <li><%= errors.get(i) %></li>
            <% } %>
        </ul>
    </div>
<% } %>

<p>Fields with a <span class="required">*</span> are required.</p>

<form action="books" method="post" class="book-form">

    <% if (editing == true) { %>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= book.getId() %>">
    <% } else { %>
        <input type="hidden" name="action" value="add">
    <% } %>

    <div class="row">
        <div class="col">
            <label for="title">Title <span class="required">*</span></label>
            <input type="text" id="title" name="title" maxlength="255" required placeholder="e.g. The Hobbit" value="<%= HtmlHelper.escape(book.getTitle()) %>">
        </div>
        <div class="col">
            <label for="author">Author <span class="required">*</span></label>
            <input type="text" id="author" name="author" minlength="2" maxlength="150" required placeholder="e.g. J.R.R. Tolkien" value="<%= HtmlHelper.escape(book.getAuthor()) %>">
        </div>
    </div>

    <div class="row">
        <div class="col">
            <label for="isbn">ISBN</label>
            <input type="text" id="isbn" name="isbn" maxlength="17" placeholder="10 or 13 digits" value="<%= HtmlHelper.escape(book.getIsbn()) %>">
        </div>
        <div class="col">
            <label for="genre">Genre <span class="required">*</span></label>
            <select id="genre" name="genre" required>
                <option value="">-- Select a genre --</option>
                <% for (int i = 0; i < genres.length; i++) { %>
                    <% if (genres[i].equals(book.getGenre())) { %>
                        <option value="<%= genres[i] %>" selected><%= genres[i] %></option>
                    <% } else { %>
                        <option value="<%= genres[i] %>"><%= genres[i] %></option>
                    <% } %>
                <% } %>
            </select>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <label for="publicationYear">Publication Year</label>
            <input type="number" id="publicationYear" name="publicationYear" min="1450" max="<%= thisYear %>" placeholder="YYYY" value="<%= HtmlHelper.escape(book.getPublicationYear()) %>">
        </div>
        <div class="col">
            <label for="pageCount">Page Count</label>
            <input type="number" id="pageCount" name="pageCount" min="1" max="10000" value="<%= HtmlHelper.escape(book.getPageCount()) %>">
        </div>
    </div>

    <div class="row">
        <div class="col">
            <label>Format <span class="required">*</span></label>
            <div class="choices">
                <label class="choice">
                    <input type="radio" name="format" value="hardcover" required <% if (book.getFormat().equals("hardcover")) { %>checked<% } %>> Hardcover
                </label>
                <label class="choice">
                    <input type="radio" name="format" value="paperback" <% if (book.getFormat().equals("paperback")) { %>checked<% } %>> Paperback
                </label>
                <label class="choice">
                    <input type="radio" name="format" value="ebook" <% if (book.getFormat().equals("ebook")) { %>checked<% } %>> eBook
                </label>
                <label class="choice">
                    <input type="radio" name="format" value="audiobook" <% if (book.getFormat().equals("audiobook")) { %>checked<% } %>> Audiobook
                </label>
            </div>
        </div>
        <div class="col">
            <label for="readingStatus">Reading Status <span class="required">*</span></label>
            <select id="readingStatus" name="readingStatus" required>
                <option value="want_to_read" <% if (book.getReadingStatus().equals("want_to_read")) { %>selected<% } %>>Want to Read</option>
                <option value="reading" <% if (book.getReadingStatus().equals("reading")) { %>selected<% } %>>Currently Reading</option>
                <option value="finished" <% if (book.getReadingStatus().equals("finished")) { %>selected<% } %>>Finished</option>
            </select>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <label>Rating</label>
            <div class="choices">
                <label class="choice">
                    <input type="radio" name="rating" value="" <% if (book.getRating().equals("")) { %>checked<% } %>> No rating
                </label>
                <label class="choice">
                    <input type="radio" name="rating" value="1" <% if (book.getRating().equals("1")) { %>checked<% } %>> 1&#9733;
                </label>
                <label class="choice">
                    <input type="radio" name="rating" value="2" <% if (book.getRating().equals("2")) { %>checked<% } %>> 2&#9733;
                </label>
                <label class="choice">
                    <input type="radio" name="rating" value="3" <% if (book.getRating().equals("3")) { %>checked<% } %>> 3&#9733;
                </label>
                <label class="choice">
                    <input type="radio" name="rating" value="4" <% if (book.getRating().equals("4")) { %>checked<% } %>> 4&#9733;
                </label>
                <label class="choice">
                    <input type="radio" name="rating" value="5" <% if (book.getRating().equals("5")) { %>checked<% } %>> 5&#9733;
                </label>
            </div>
        </div>
        <div class="col">
            <label for="dateFinished">Date Finished <small>(required if Finished)</small></label>
            <input type="date" id="dateFinished" name="dateFinished" value="<%= HtmlHelper.escape(book.getDateFinished()) %>">
        </div>
    </div>

    <div class="row">
        <div class="col">
            <label class="choice">
                <input type="checkbox" name="owned" value="yes" <% if (book.isOwned()) { %>checked<% } %>> I own a copy
            </label>
        </div>
    </div>

    <label for="notes">Notes</label>
    <textarea id="notes" name="notes" rows="5" maxlength="1000" placeholder="Thoughts, favorite quotes... (max 1,000 characters)"><%= HtmlHelper.escape(book.getNotes()) %></textarea>

    <div class="buttons">
        <button type="submit" class="button">Save Book</button>
        <% if (editing == true) { %>
            <a href="books?action=view&id=<%= book.getId() %>" class="button button-gray">Cancel</a>
        <% } else { %>
            <a href="books?action=list" class="button button-gray">Cancel</a>
        <% } %>
    </div>
</form>

<%@ include file="footer.jsp" %>
