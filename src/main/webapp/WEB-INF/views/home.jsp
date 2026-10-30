<%@ page contentType="text/html;charset=UTF-8" %>
<% String pageTitle = "Home"; %>
<%@ include file="header.jsp" %>

<h1>Welcome to ShelfLife!</h1>
<p>Keep track of the books you own and the books you are reading.</p>

<h2>My Collection</h2>
<div class="stats">
    <div class="stat-box">
        <div class="number"><%= request.getAttribute("total") %></div>
        <div>Total Books</div>
    </div>
    <div class="stat-box">
        <div class="number"><%= request.getAttribute("reading") %></div>
        <div>Currently Reading</div>
    </div>
    <div class="stat-box">
        <div class="number"><%= request.getAttribute("finished") %></div>
        <div>Finished</div>
    </div>
    <div class="stat-box">
        <div class="number"><%= request.getAttribute("wantToRead") %></div>
        <div>Want to Read</div>
    </div>
</div>

<p>
    <a href="books?action=new" class="button">+ Add a Book</a>
    <a href="books?action=list" class="button button-gray">View My Library</a>
</p>

<%@ include file="footer.jsp" %>
