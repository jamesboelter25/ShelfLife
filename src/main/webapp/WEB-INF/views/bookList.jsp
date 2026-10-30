<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.shelflife.Book, com.shelflife.HtmlHelper, java.util.ArrayList" %>
<% String pageTitle = "My Library"; %>
<%@ include file="header.jsp" %>

<%
    ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("books");

    // I get what was searched so it stays in my search box
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

    String message = request.getParameter("message");
    if (message == null) {
        message = "";
    }

    String[] genres = {"Fiction", "Mystery/Thriller", "Science Fiction", "Fantasy", "Romance",
            "Historical Fiction", "Non-Fiction", "Biography/Memoir", "Self-Help",
            "History", "Science", "Graphic Novel", "Poetry", "Other"};
%>

<h1>My Library</h1>

<% if (message.equals("added")) { %>
    <div class="success">The book was added to your library.</div>
<% } %>
<% if (message.equals("deleted")) { %>
    <div class="success">The book was deleted.</div>
<% } %>

<!-- my search box and filters -->
<form action="books" method="get" class="search-form">
    <input type="hidden" name="action" value="list">

    <input type="text" name="search" placeholder="Search title or author" value="<%= HtmlHelper.escape(search) %>">

    <select name="genre">
        <option value="">All Genres</option>
        <% for (int i = 0; i < genres.length; i++) { %>
            <% if (genres[i].equals(genre)) { %>
                <option value="<%= genres[i] %>" selected><%= genres[i] %></option>
            <% } else { %>
                <option value="<%= genres[i] %>"><%= genres[i] %></option>
            <% } %>
        <% } %>
    </select>

    <select name="status">
        <option value="">All Statuses</option>
        <option value="want_to_read" <% if (status.equals("want_to_read")) { %>selected<% } %>>Want to Read</option>
        <option value="reading" <% if (status.equals("reading")) { %>selected<% } %>>Currently Reading</option>
        <option value="finished" <% if (status.equals("finished")) { %>selected<% } %>>Finished</option>
    </select>

    <button type="submit" class="button">Search</button>
    <a href="books?action=list">Clear</a>
</form>

<p><%= books.size() %> book(s) found.</p>

<% if (books.size() == 0) { %>

    <p>No books here yet. <a href="books?action=new">Add one!</a></p>

<% } else { %>

    <table>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th class="hide-mobile">Genre</th>
            <th class="hide-mobile">Format</th>
            <th>Status</th>
            <th class="hide-mobile">Rating</th>
            <th></th>
        </tr>
        <% for (int i = 0; i < books.size(); i++) { %>
            <% Book b = books.get(i); %>
            <tr>
                <td><%= HtmlHelper.escape(b.getTitle()) %></td>
                <td><%= HtmlHelper.escape(b.getAuthor()) %></td>
                <td class="hide-mobile"><%= HtmlHelper.escape(b.getGenre()) %></td>
                <td class="hide-mobile"><%= b.getFormatLabel() %></td>
                <td><%= b.getStatusLabel() %></td>
                <td class="hide-mobile stars"><%= b.getStars() %></td>
                <td><a href="books?action=view&id=<%= b.getId() %>">View</a></td>
            </tr>
        <% } %>
    </table>

<% } %>

<%@ include file="footer.jsp" %>
