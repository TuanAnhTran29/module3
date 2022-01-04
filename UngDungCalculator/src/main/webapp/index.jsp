<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 04-Jan-22
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <h1>Simple Calculator</h1>
    <form method="post" action="/CalculatorServlet">
      <h4>Calculator</h4>
      <br/>
      <label>First operand:</label>
      <input type="number" name="firstNum" placeholder="Enter first number:">
      <br/>
      <label>Operator:</label>
      <select name="operator">
        <option value="+">Addition</option>
        <option value="-">Subtraction</option>
        <option value="*">Multiplication</option>
        <option value="/">Division</option>
      </select>
      <br/>
      <label>Second operand:</label>
      <input type="number" name="secondNum" placeholder="Enter second number:">
      <br/>
      <input type="submit" value="Calculate">
    </form>
  </body>
</html>
