<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 11-Jan-22
  Time: 12:27 AM
  To change this template use File | Settings | File Templates.
--%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        @media (min-width: 1025px) {
            .h-custom {
                height: 100vh !important;
            }
        }
    </style>
</head>
<body>
<section class="h-100 h-custom" style="background-color: #8fc4b7;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-8 col-xl-6">
                <div class="card rounded-3">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img3.webp" class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;" alt="Sample photo">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Registration Info</h3>
                        <c:if test="${requestScope['message']!= null}">
                            <span class="message" style="color: red">${requestScope['message']}</span>
                        </c:if>

                        <form method="post" action="/accounts?action=createLibrarian" class="px-md-2">

                            <div class="form-outline mb-4">
                                <input name="librarianName" type="text" id="form3Example1q" class="form-control" />
                                <label class="form-label" for="form3Example1q">Name</label>
                            </div>

                            <div class="row mb-4 pb-2 pb-md-0 mb-md-5">
                                <div class="col-md-6">

                                    <div class="form-outline">
                                        <input value="${account_id}" name="accountId" type="hidden" id="form3Example1w" class="form-control" />
                                    </div>

                                </div>
                            </div>

                            <button type="submit" class="btn btn-success btn-lg mb-1">Submit</button>

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
