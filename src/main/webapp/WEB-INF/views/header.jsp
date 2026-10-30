<%-- my header - I include this at the top of every page --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ShelfLife - <%= pageTitle %></title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>

<div class="header">
    <a href="books?action=home" class="logo">&#128218; ShelfLife</a>
    <button class="menu-button" onclick="toggleMenu()">&#9776;</button>
    <div id="nav" class="nav">
        <a href="books?action=home">Home</a>
        <a href="books?action=list">My Library</a>
        <a href="books?action=new">Add Book</a>
        <a href="about.jsp">About</a>
    </div>
</div>

<div class="container">
