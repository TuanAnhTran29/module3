<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 12-Jan-22
  Time: 11:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .gradient-custom {
            /* fallback for old browsers */
            background: #f093fb;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1))
        }

        .card-registration .select-input.form-control[readonly]:not([disabled]) {
            font-size: 1rem;
            line-height: 2.15;
            padding-left: .75em;
            padding-right: .75em;
        }
        .card-registration .select-arrow {
            top: 13px;
        }
    </style>
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
                    <a class="nav-link active" aria-current="page" href="/librarians">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/loancards?action=showLoanCardByLibrarian&studentId=${student.getId()}">Card</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/librarians?action=showAddBookForm&studentId=${student.getId()}">Add Book</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/librarians?action=viewAccountByLibrarian&studentId=${student.getId()}">View Student Account</a>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Update Book Form</h3>
                        <form method="post" action="/librarians?action=updateBook">

                            <div class="row">
                                <div class="col-md-6 mb-4">

                                    <div class="form-outline">
                                        <input name="bookName" type="text" id="bookName" value="<c:out value="${book.getName()}"></c:out>" class="form-control form-control-lg" />
                                        <label class="form-label" for="bookName">Book Name</label>
                                    </div>

                                </div>

                            </div>
                            <div class="col-md-6 mb-4">

                                <div class="form-outline">
                                    <input name="author" type="text" id="author" value="<c:out value="${book.getAuthor()}"></c:out>" class="form-control form-control-lg" />
                                    <label class="form-label" for="author">Author</label>
                                </div>

                                <div class="form-outline">
                                    <input name="description" type="text" id="description" value="<c:out value="${book.getDescription()}"></c:out>" class="form-control form-control-lg" />
                                    <label class="form-label" for="description">Description</label>
                                </div>

                            </div>

                            <div class="form-outline">
                                <input name="quantity" type="number" id="quantity" value="<c:out value="${book.getQuantity()}"></c:out>" class="form-control form-control-lg" />
                                <label class="form-label" for="quantity">Quantity</label>
                            </div>


                            <input type="hidden" value="${book.getId()}" name="bookId">
                            <input type="hidden" value="${librarian.getId()}" name="librarianId">
                            <div class="mt-4 pt-2">
                                <input class="btn btn-primary btn-lg" type="submit" value="Save" />
                            </div>


                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</html>
