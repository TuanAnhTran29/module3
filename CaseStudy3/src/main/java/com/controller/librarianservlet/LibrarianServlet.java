package com.controller.librarianservlet;

import com.model.*;
import com.service.accountservice.AccountService;
import com.service.accountservice.IAccountService;
import com.service.bookservice.BookService;
import com.service.bookservice.IBookService;
import com.service.librarianservice.ILibrarianService;
import com.service.librarianservice.LibrarianService;
import com.service.loancardservice.ILoanCardService;
import com.service.loancardservice.LoanCardService;
import com.service.loancardstatusservice.ILoanCardStatusService;
import com.service.loancardstatusservice.LoanCardStatusService;
import com.service.statusservice.IStatusService;
import com.service.statusservice.StatusService;
import com.service.studentservice.IStudentService;
import com.service.studentservice.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "LibrarianServlet", value = "/librarians")
public class LibrarianServlet extends HttpServlet {
    private IStatusService statusService= new StatusService();
    private ILoanCardStatusService loanCardStatusService= new LoanCardStatusService();
    private ILibrarianService librarianService= new LibrarianService();
    private ILoanCardService loanCardService= new LoanCardService();
    private IBookService bookService = new BookService();
    private IAccountService accountService= new AccountService();
    private IStudentService studentService= new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "showAddBookForm":
                showAddBookForm(request,response);
                break;
            case "showUpdateForm":
                showUpdateForm(request,response);
                break;
            case "deleteBook":
                deleteBook(request,response);
                break;
            case "viewAccountByLibrarian":
                viewAccount(request,response);
                break;
            case "changeStatus":
                changeStatus(request,response);
                break;
            default:
                listBook(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action){
            case "updateBook":
                updateBook(request,response);
                break;
            case "searchLoanCardByAdmin":
                searchCardByStudentName(request,response);
                break;
            case "searchAccount":
                searchAccount(request,response);
                break;
        }

    }

    private void searchAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username= request.getParameter("search");

            Account studentAccount= accountService.findByUserName(username);
            if(studentAccount != null){
                request.setAttribute("studentAccount",studentAccount);

            }else{
                request.setAttribute("message","Can not find this student");
            }

            RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchAccount.jsp");
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchCardByStudentName(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<LoanCard> loanCardList= new ArrayList<>();
            String studentName= request.getParameter("search");
            for(LoanCard l : loanCardService.findAll()) {
                if (l.getStudent().getName().equals(studentName)) {
                    loanCardList.add(l);
                }
            }
            if(loanCardList.isEmpty()){
                request.setAttribute("message","Can not find this student");
            }


            request.setAttribute("loanCardList",loanCardList);

            RequestDispatcher requestDispatcher= request.getRequestDispatcher("search/searchLoanCardByAdmin.jsp");
            requestDispatcher.forward(request,response);
        }catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Book> bookList = bookService.findAll();
            request.setAttribute("bookList", bookList);

            HttpSession session = request.getSession();
            Account loggingAccount = (Account) session.getAttribute("accountLogging");

            List<Librarian> librarianList = librarianService.findAll();
            Librarian librarian= new Librarian();
            for (Librarian l : librarianList) {
                if (l.getAccount().getId() == loggingAccount.getId()) {
                    librarian = l;
                    break;
                }
            }

            request.setAttribute("librarian", librarian);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("librarian/librarianHomePage.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showAddBookForm(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("book/addBookForm.jsp");

        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showUpdateForm(HttpServletRequest request,HttpServletResponse response){
        int id= Integer.parseInt(request.getParameter("bookId")) ;
        Book book= bookService.findById(id);

        request.setAttribute("book",book);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("book/updateBookForm.jsp");

        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBook(HttpServletRequest request,HttpServletResponse response){
        int id= Integer.parseInt(request.getParameter("bookId"));
        String bookName= request.getParameter("bookName");
        String author= request.getParameter("author");
        String description= request.getParameter("description");
        int quantity= Integer.parseInt(request.getParameter("quantity"));
        Book book= new Book(id,bookName,author,description,quantity);

        bookService.update(id,book);

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

    private void deleteBook(HttpServletRequest request,HttpServletResponse response){
        int id= Integer.parseInt(request.getParameter("bookId"));
        Book book=bookService.findById(id);

        bookService.delete(book.getId());

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

    private void viewAccount(HttpServletRequest request, HttpServletResponse response){
        List<Account> studentAccount= new ArrayList<>();
        for (Account a: accountService.findAll()){
            if (a.getRole().getId() == 1){
                studentAccount.add(a);
            }
        }

        request.setAttribute("accountList",studentAccount);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/viewAllAccount.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeStatus(HttpServletRequest request,HttpServletResponse response){
        int id= Integer.parseInt(request.getParameter("accountId"));
        Account account= accountService.findById(id);

        if (account.getStatus().getId()== 1){
            account.getStatus().setId(2);
            accountService.update(account.getId(), account);
        }else {
            account.getStatus().setId(1);
            accountService.update(account.getId(), account);
        }



        List<Account> studentAccount= new ArrayList<>();
        for (Account a: accountService.findAll()){
            if (a.getRole().getId() == 1){
                studentAccount.add(a);
            }
        }

        request.setAttribute("accountList",studentAccount);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/accountListChanged.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
