<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 12-Jan-22
  Time: 10:05 PM
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
        <a class="navbar-brand" href="#">Librarian</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/librarians">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/loancards?action=showLoanCardByLibrarian">Card</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/librarians?action=showAddBookForm">Add Book</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/librarians?action=viewAccountByLibrarian">View Student Account</a>
                </li>
            </ul>
            <form method="post" action="/librarians?action=searchLoanCardByAdmin" class="d-flex">
                <input name="search" class="form-control me-2" type="search" placeholder="Search loan card" aria-label="Search">
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
        <h1>Loan Card List</h1>
    </center>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Card Code</th>
            <th scope="col">Book Name</th>
            <th scope="col">Student Name</th>
            <th scope="col">Class Name</th>
            <th scope="col">Borrow Date</th>
            <th scope="col">Return Date</th>
            <th scope="col">Status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${loanCardList}" var="loancard">
            <tr>
                <th scope="row"><c:out value="${loancard.getCode()}"/></th>
                <td><c:out value="${loancard.getBook().getName()}"/></td>
                <td><c:out value="${loancard.getStudent().getName()}"/></td>
                <td><c:out value="${loancard.getStudent().getStudentClass()}"/></td>
                <td><c:out value="${loancard.getDayBorrow()}"/></td>
                <td><c:out value="${loancard.getDayReturn()}"/></td>
                <td><c:out value="${loancard.getStatus().getType()}"/></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</html>
