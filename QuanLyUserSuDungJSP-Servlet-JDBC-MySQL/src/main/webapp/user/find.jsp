<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 06-Jan-22
  Time: 9:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find user by country</title>
</head>
<body>
    <p>
        <a href="/users">Back to user list</a>
    </p>
    <c:if test="${requestScope['users'] != null}">
        <p>${requestScope['message']}</p>
    </c:if>

    <form method="post">
        <p>Enter the country</p>
        <input type="text" name="country" id="country">
        <input type="submit" value="Find">
    </form>
    
    <c:if test="${requestScope['users'] != null}">
        <table border="1" cellpadding="5">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.getId()}"/></td>
                    <td><c:out value="${user.getName()}"/></td>
                    <td><c:out value="${user.getEmail()}"/></td>
                    <td><c:out value="${user.getCountry()}"/></td>
                    <td>
                        <a href="/users?action=edit&id=${user.getId()}">Edit</a>
                        <a href="/users?action=delete&id=${user.getId()}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
