package com.controller.bookservlet;

import com.model.Book;
import com.model.LoanCard;
import com.model.Student;
import com.service.bookservice.BookService;
import com.service.bookservice.IBookService;
import com.service.loancardservice.ILoanCardService;
import com.service.loancardservice.LoanCardService;
import com.service.studentservice.IStudentService;
import com.service.studentservice.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    protected IStudentService studentService= new StudentService();
    private ILoanCardService loanCardService= new LoanCardService();
    private IBookService bookService= new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "searchBookAdmin":
                searchBookAdmin(request,response);
                break;
            case "addBook":
                addBook(request,response);
                break;
            case "searchBook":
                searchBookStudent(request,response);
                break;
        }

    }

    private void searchBookStudent(HttpServletRequest request, HttpServletResponse response) {
        String name= request.getParameter("search");
        int studentId= Integer.parseInt(request.getParameter("studentId"));

        Student student= studentService.findById(studentId);

        Book book= bookService.findByBookName(name);
        if(book != null){
            request.setAttribute("book",book);
            request.setAttribute("student",student);
        }else{
            request.setAttribute("message","Can not find this book!");
            request.setAttribute("student",student);
        }


        RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchBook.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Book> bookList= bookService.findAll();
            request.setAttribute("bookList",bookList);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/studentHomePage.jsp");
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchBookAdmin(HttpServletRequest request,HttpServletResponse response){
        String name= request.getParameter("search");

        Book book= bookService.findByBookName(name);
        if(book != null){
            request.setAttribute("book",book);
        }else{
            request.setAttribute("message","Can not find this book!");
        }


        RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchBookAdmin.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response){
//        int id= Integer.parseInt(request.getParameter("id"));
        String bookName= request.getParameter("bookName");
        String author= request.getParameter("author");
        String description= request.getParameter("description");
        int quantity= Integer.parseInt(request.getParameter("quantity"));

        List<Book> bookList= bookService.findAll();
        Book book= bookService.findByBookName(bookName);

        for (Book b: bookList){
            if (b.getName().equals(bookName)){
                book= b;
                book.setQuantity(book.getQuantity() + quantity);
                book.setDescription(description);
                bookService.update(book.getId(), book);


                request.setAttribute("bookList",bookService.findAll());
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("librarian/librarianHomePage.jsp");
                try {
                    requestDispatcher.forward(request,response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Book book1= new Book(bookName,author,description,quantity);
                bookService.add(book1);

                request.setAttribute("bookList",bookService.findAll());
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("librarian/librarianHomePage.jsp");
                try {
                    requestDispatcher.forward(request,response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
