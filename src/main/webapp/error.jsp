<%@ page contentType="text/html;charset=UTF-8" %>
<% String pageTitle = "Error"; %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null) {
        errorMessage = "Sorry, that page or book could not be found.";
    }
%>

<h1>Oops!</h1>
<div class="error-box"><%= errorMessage %></div>
<p><a href="books?action=home" class="button">Go to the Home Page</a></p>

<%@ include file="/WEB-INF/views/footer.jsp" %>
