<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 30-Dec-21
  Time: 9:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form method="post" action="/display-discount">
    <input type="text" name="description" placeholder="Enter product description">
    <input type="number" name="price" placeholder="Enter list price">
    <input type="number" name="discount" placeholder="Enter discount percent">
    <input type="submit" name="calculate" value="Calculate Discount">
  </form>

  </body>
</html>
