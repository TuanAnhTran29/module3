<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Customer" %>
<%--
  Created by IntelliJ IDEA.
  User: tuana
  Date: 04-Jan-22
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Danh sach khach hang</title>
  </head>
  <body>
    <%
      List<Customer> customerList= new ArrayList<>();
      customerList.add(new Customer("Mai Văn Hoàn","1983-08-20","Hà Nội","https://kenh14cdn.com/thumb_w/660/2020/9/17/dsc01857a-16002833291082004525924.jpg"));
      customerList.add(new Customer("Nguyễn Văn Nam","1983-08-21","Bắc Giang","https://cdn.nguyenkimmall.com/images/detailed/555/may-anh-cho-nguoi-moi.jpg"));
      customerList.add(new Customer("Nguyễn Thái Hòa","1983-08-22","Nam Định","https://cellphones.com.vn/sforum/wp-content/uploads/2018/11/2-9.png"));
      customerList.add(new Customer("Trần Đăng Khoa","1983-08-17","Hà Tây","https://static2.yan.vn/YanNews/202005/202005220338210409-bee6f138-0608-4d56-bce3-27148a855654.png"));
      customerList.add(new Customer("Nguyễn Đình Thi","1983-08-19","Hà Nội","https://bloganh.net/wp-content/uploads/2020/06/chup-anh-sat-mat-nuoc-cuc-dep.jpg"));
      request.setAttribute("customerList",customerList);
    %>


    <h1>Danh sách khách hàng</h1>
    <table style="border: 1px black solid">
      <tr style="border: 1px black solid">
        <th>Tên</th>
        <th>Ngày sinh</th>
        <th>Địa chỉ</th>
        <th>Ảnh</th>
      </tr>
      <c:forEach items="${customerList}" var="customer">
        <tr style="border: 1px black solid">
          <td>${customer.name}</td>
          <td>${customer.dateOfBirth}</td>
          <td>${customer.address}</td>
          <td><img width="100px" height="100px" src="${customer.image}" alt=""></td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
