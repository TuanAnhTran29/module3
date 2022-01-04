<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 04-Jan-22
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dictionary</title>
</head>
<body>
    <%
        Map<String,String> dic= new HashMap<>();

        dic.put("hello","Xin chào");
        dic.put("how","Thế nào");
        dic.put("book","Quyển sách");
        dic.put("computer","Máy tính");
        dic.put("toy","Đồ chơi");

        String search= request.getParameter("inputWord");

        String result= dic.get(search);

        if (result != null) {
            System.out.println("Word: " + search);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Not found");
        }

    %>


</body>
</html>
