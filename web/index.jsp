<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="style/main.css">
</head>
<body>

<%--Tabela usera koju cemo popuniti asinhrono sa javascript-om--%>
<h3>Users</h3>

<button id="load-users-btn">Ucitaj korisnike</button>

<table id="users-tbl">
    <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
    </thead>
    <tbody>

    </tbody>
</table>

<%--Novi redovi--%>
<br><br>
<form id="add-user-form">
    <input type="text" name="firstName" placeholder="First Name">
    <input type="text" name="lastName" placeholder="Last Name">
    <input type="Submit" value="Submit">
</form>
<script src="js/script.js"></script>
</body>
</html>
