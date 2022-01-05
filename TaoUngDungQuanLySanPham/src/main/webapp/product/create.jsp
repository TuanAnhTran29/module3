<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 05-Jan-22
  Time: 9:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create product</title>
</head>
<body>
<h1>Create new customer</h1>

<p>
    <c:if test="${requestScope['message']!= null}">
        <span class="message">${requestScope['message']}</span>
    </c:if>
</p>
<p>
    <a href="/products">Back to product list</a>
</p>
    <form method="post">
        <fieldset>
            <legend>Product information</legend>
            <table>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="name" id="name"></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="number" name="price" id="price"></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><input type="text" name="description" id="description"></td>
                </tr>
                <tr>
                    <td>Producer</td>
                    <td><input type="text" name="producer" id="producer"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Create product"></td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>
