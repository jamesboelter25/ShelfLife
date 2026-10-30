<%@ page contentType="text/html;charset=UTF-8" %>
<% String pageTitle = "About"; %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<h1>About ShelfLife</h1>

<p>
    ShelfLife is my final project for CIT345 (Modern Web &amp; Cloud Application Development).
    I made it because my book collection got too big to remember. I kept buying books I already
    had and forgetting which ones I finished.
</p>

<h2>What it does</h2>
<ul>
    <li>Add books to my library with a form</li>
    <li>See all my books and search by title or author</li>
    <li>Filter by genre or reading status</li>
    <li>Edit a book (for example when I finish it and want to rate it)</li>
    <li>Delete books I don't have anymore</li>
</ul>

<h2>How it was made</h2>
<ul>
    <li><strong>Frontend:</strong> HTML, CSS, JavaScript and JSP pages</li>
    <li><strong>Controller:</strong> Java Servlet (BookServlet)</li>
    <li><strong>Database:</strong> MySQL</li>
    <li><strong>Server:</strong> Apache Tomcat</li>
</ul>

<%@ include file="/WEB-INF/views/footer.jsp" %>
