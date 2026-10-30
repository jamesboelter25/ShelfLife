<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.shelflife.Book, com.shelflife.HtmlHelper" %>
<% String pageTitle = "Book Details"; %>
<%@ include file="header.jsp" %>

<%
    Book book = (Book) request.getAttribute("book");

    String message = request.getParameter("message");
    if (message == null) {
        message = "";
    }
%>

<% if (message.equals("updated")) { %>
    <div class="success">Your changes were saved.</div>
<% } %>

<h1><%= HtmlHelper.escape(book.getTitle()) %></h1>
<p class="author">by <%= HtmlHelper.escape(book.getAuthor()) %></p>

<table class="details">
    <tr>
        <th>ISBN</th>
        <td>
            <% if (book.getIsbn().equals("")) { %>
                -
            <% } else { %>
                <%= HtmlHelper.escape(book.getIsbn()) %>
            <% } %>
        </td>
    </tr>
    <tr>
        <th>Genre</th>
        <td><%= HtmlHelper.escape(book.getGenre()) %></td>
    </tr>
    <tr>
        <th>Format</th>
        <td><%= book.getFormatLabel() %></td>
    </tr>
    <tr>
        <th>Publication Year</th>
        <td>
            <% if (book.getPublicationYear().equals("")) { %>
                -
            <% } else { %>
                <%= book.getPublicationYear() %>
            <% } %>
        </td>
    </tr>
    <tr>
        <th>Page Count</th>
        <td>
            <% if (book.getPageCount().equals("")) { %>
                -
            <% } else { %>
                <%= book.getPageCount() %>
            <% } %>
        </td>
    </tr>
    <tr>
        <th>Reading Status</th>
        <td><%= book.getStatusLabel() %></td>
    </tr>
    <tr>
        <th>Rating</th>
        <td class="stars"><%= book.getStars() %></td>
    </tr>
    <tr>
        <th>Date Finished</th>
        <td>
            <% if (book.getDateFinished().equals("")) { %>
                -
            <% } else { %>
                <%= book.getDateFinished() %>
            <% } %>
        </td>
    </tr>
    <tr>
        <th>I Own a Copy</th>
        <td>
            <% if (book.isOwned()) { %>
                Yes
            <% } else { %>
                No
            <% } %>
        </td>
    </tr>
    <tr>
        <th>Notes</th>
        <% if (book.getNotes().equals("")) { %>
            <td>-</td>
        <% } else { %>
            <td class="notes"><%= HtmlHelper.escape(book.getNotes()) %></td>
        <% } %>
    </tr>
</table>

<div class="buttons">
    <a href="books?action=edit&id=<%= book.getId() %>" class="button">Edit</a>

    <!-- my delete has to be a form with POST. I used confirm() so it asks if I'm sure first -->
    <form action="books" method="post" class="inline-form" onsubmit="return confirm('Are you sure you want to delete this book?');">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= book.getId() %>">
        <button type="submit" class="button button-red">Delete</button>
    </form>

    <a href="books?action=list" class="button button-gray">Back to Library</a>
</div>

<%@ include file="footer.jsp" %>
