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
            case "searchBook":
                searchBook(request,response);
                break;
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

    private void searchBook(HttpServletRequest request,HttpServletResponse response){
        int studentId= Integer.parseInt(request.getParameter("studentId"));
        String name= request.getParameter("search");

        Book book= bookService.findByBookName(name);
        List<LoanCard> loanCardList= loanCardService.findByStudentId(studentId);
        Student student= studentService.findById(studentId);


        request.setAttribute("student",student);
        request.setAttribute("bookList",bookService.findAll());
        request.setAttribute("loancards",loanCardList);
        request.setAttribute("message","Can not find this book!");
        request.setAttribute("book",book);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchBook.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
