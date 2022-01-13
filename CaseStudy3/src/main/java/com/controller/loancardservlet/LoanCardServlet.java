package com.controller.loancardservlet;

import com.model.*;
import com.service.bookservice.BookService;
import com.service.bookservice.IBookService;
import com.service.loancardservice.ILoanCardService;
import com.service.loancardservice.LoanCardService;
import com.service.loancardstatusservice.ILoanCardStatusService;
import com.service.loancardstatusservice.LoanCardStatusService;
import com.service.studentservice.IStudentService;
import com.service.studentservice.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoanCardServlet", value = "/loancards")
public class LoanCardServlet extends HttpServlet {
    private IBookService bookService= new BookService();
    private ILoanCardService loanCardService= new LoanCardService();
    private IStudentService studentService= new StudentService();
    private ILoanCardStatusService loanCardStatusService=new LoanCardStatusService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "showLoanCardByStudent":
                showLoanCardByStudent(request,response);
                break;
            case "showLoanCardByLibrarian":
                showLoanCardByLibrarian(request,response);
                break;
            case "returnBook":
                returnBook(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "searchLoanCard":
                searchLoanCard(request,response);
                break;
        }
    }

    private void showLoanCardByStudent(HttpServletRequest request,HttpServletResponse response){
        int studentId= Integer.parseInt(request.getParameter("studentId"));
        List<LoanCard> loanCardList= loanCardService.findByStudentId(Integer.parseInt(request.getParameter("studentId")));

        Student student= studentService.findById(studentId);

        request.setAttribute("student",student);
        request.setAttribute("loancards",loanCardList);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("loancard/loanCardListOfStudent.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response){
        LoanCard loanCard= loanCardService.findById(Integer.parseInt(request.getParameter("cardId")));
        LoanCardStatus loanCardStatus= loanCardStatusService.findById(1);
        loanCard.setStatus(loanCardStatus);
        Book book= loanCard.getBook();

        book.setQuantity(book.getQuantity() + 1);
        loanCardService.update(loanCard.getId(), loanCard);
        bookService.update(book.getId(),book);

        request.setAttribute("bookList",bookService.findAll());
        request.setAttribute("student",loanCard.getStudent());
        request.setAttribute("message","Returned book successfully");
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/studentHomePage.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLoanCardByLibrarian(HttpServletRequest request,HttpServletResponse response){
        List<LoanCard> loanCardList= loanCardService.findAll();


        request.setAttribute("loanCardList",loanCardList);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("loancard/loanCardListAll.jsp");

        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchLoanCard(HttpServletRequest request,HttpServletResponse response){
        int studentId= Integer.parseInt(request.getParameter("studentId"));
        String bookName= request.getParameter("search");
        List<LoanCard> loanCardList= loanCardService.findByBookName(bookName);
        Student student= studentService.findById(studentId);

        request.setAttribute("student",student);
        request.setAttribute("loancards",loanCardList);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchLoanCard.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
