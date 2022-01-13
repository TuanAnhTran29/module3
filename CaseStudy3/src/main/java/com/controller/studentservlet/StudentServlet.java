package com.controller.studentservlet;

import com.model.*;
import com.service.accountservice.AccountService;
import com.service.accountservice.IAccountService;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@WebServlet(name = "StudentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
    private ILoanCardStatusService loanCardStatusService= new LoanCardStatusService();
    private IAccountService accountService= new AccountService();
    private IBookService bookService= new BookService();
    private IStudentService studentService=new StudentService();
    private ILoanCardService loanCardService= new LoanCardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "showBorrowBookForm":
                showBorrowBookForm(request,response);
                break;
            default:
                listBook(request,response);
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
            case "borrowBook":
                borrowBook(request,response);
                break;
        }
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Book> bookList= bookService.findAll();
            request.setAttribute("bookList",bookList);

            HttpSession session= request.getSession();
            Account loggingAccount= (Account) session.getAttribute("accountLogging");

            List<Student> studentList= studentService.findAll();
            Student student1= new Student();
            for (Student st: studentList){
                if (st.getAccount().getId() == loggingAccount.getId()){
                    student1= st;
                    break;
                }
            }

            request.setAttribute("student",student1);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/studentHomePage.jsp");
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBorrowBookForm(HttpServletRequest request, HttpServletResponse response){
        try {
            Random rnd = new Random();
            int number = rnd.nextInt(9999);
            String code="MS-" + String.format("%04d",number);
            List<String> codeList= loanCardService.getAllCode();
            while (codeList.contains(code)){
                number = rnd.nextInt(9999);
                code="MS-" + String.format("%04d",number);
            }
            int bookId= Integer.parseInt( request.getParameter("bookId"));
            int studentId= Integer.parseInt(request.getParameter("studentId"));
            LocalDate borrowBookDate=LocalDate.now();

            Book book= bookService.findById(bookId);
            Student student= studentService.findById(studentId);


            request.setAttribute("borrowDate",borrowBookDate);
            request.setAttribute("book",book);
            request.setAttribute("student",student);
            request.setAttribute("code",code);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("book/borrowBookForm.jsp");
            requestDispatcher.forward(request,response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response){
        String code= request.getParameter("code");
        int studentId= Integer.parseInt(request.getParameter("studentId"));
        int id= Integer.parseInt(request.getParameter("bookId"));
        Date borrowDate= Date.valueOf(request.getParameter("borrowDate"));
        Date returnDate= Date.valueOf(request.getParameter("returnDate"));
        LoanCardStatus status= loanCardStatusService.findById(2);
        Book book= bookService.findById(id);
        Student student= studentService.findById(studentId);

        book.setQuantity(book.getQuantity() - 1);
        bookService.update(book.getId(),book);

        LoanCard loanCard= new LoanCard(code,book,student,status,borrowDate,returnDate);
        loanCardService.add(loanCard);

        request.setAttribute("bookList",bookService.findAll());
        request.setAttribute("student",student);
        request.setAttribute("message","Borrow book successfully");
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/studentHomePage.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
