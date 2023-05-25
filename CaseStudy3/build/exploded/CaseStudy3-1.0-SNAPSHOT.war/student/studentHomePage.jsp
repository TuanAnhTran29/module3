<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 10-Jan-22
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Student</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/students">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/loancards?action=showLoanCardByStudent&studentId=${student.getId()}">Card</a>
                </li>
            </ul>
            <form method="post" action="/books?action=searchBook&studentId=${student.getId()}" class="d-flex">
                <input name="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<div class="container">
    <p style="color: blue">
        <c:if test="${requestScope['message']!= null}">
            <span class="message">${requestScope['message']}</span>
        </c:if>
    </p>
    <center>
        <h1>List Of Book</h1>
    </center>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Author</th>
            <th scope="col">Description</th>
            <th scope="col">Quantity</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookList}" var="book">
            <tr>
                <th scope="row"><c:out value="${book.getId()}"/></th>
                <td><c:out value="${book.getName()}"/></td>
                <td><c:out value="${book.getAuthor()}"/></td>
                <td><c:out value="${book.getDescription()}"/></td>
                <td><c:out value="${book.getQuantity()}"/></td>
                <c:if test="${book.getQuantity() > 0}">
                    <td><a href="/students?action=showBorrowBookForm&bookId=${book.getId()}&studentId=${student.getId()}">Borrow Book</a></td>
                </c:if>
                <c:if test="${book.getQuantity() <= 0}">
                    <td></td>
                </c:if>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button class="btn btn-primary"><a style="color: white;text-decoration: none" href="/accounts">Logout</a></button>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</html>
