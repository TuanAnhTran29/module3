package com.controller.accountservlet;

import com.model.*;
import com.service.accountservice.AccountService;
import com.service.accountservice.IAccountService;
import com.service.bookservice.BookService;
import com.service.bookservice.IBookService;
import com.service.librarianservice.ILibrarianService;
import com.service.librarianservice.LibrarianService;
import com.service.roleservice.IRoleService;
import com.service.roleservice.RoleService;
import com.service.statusservice.IStatusService;
import com.service.statusservice.StatusService;
import com.service.studentservice.IStudentService;
import com.service.studentservice.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AccountServlet", value = "/accounts")
public class AccountServlet extends HttpServlet {
    ILibrarianService librarianService= new LibrarianService();
    IBookService bookService= new BookService();
    IAccountService accountService=new AccountService();
    IStatusService statusService= new StatusService();
    IRoleService roleService= new RoleService();
    IStudentService studentService= new StudentService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action= request.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "showCreateAccountForm":
                showCreateAccountForm(request,response);
                break;

            default:
                showLoginForm(request,response);
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
            case "createAccount":
                createAccount(request,response);
                break;
            case "loginAccount":
                loginAccount(request,response);
                break;
            case "createStudent":
                createStudent(request,response);
                break;
            case "createLibrarian":
                createLibrarian(request,response);
                break;
        }
    }

    private void showLoginForm(HttpServletRequest request,HttpServletResponse response){
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/login.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginAccount(HttpServletRequest request,HttpServletResponse response){
        HttpSession session= request.getSession(false);
        String username= request.getParameter("username");
        String password= request.getParameter("password");
        Account account= accountService.findByUserName(username);

        try {
            if (account == null){
                request.setAttribute("message","Can not find this account");
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/login.jsp");
                requestDispatcher.forward(request,response);
            }else {
                if (account.getPassword().equals(password)){
                    session.setAttribute("accountLogging",account);
                    if (account.getRole().getId() == 1 && account.getStatus().getId() == 1){
                        List<Student> studentList= studentService.findAll();
                        Student student1= new Student();
                        for (Student st: studentList){
                            if (st.getAccount().getId() == account.getId()){
                                student1= st;
                                break;
                            }
                        }
                        HttpSession session1= request.getSession();
                        Account account1= (Account) session1.getAttribute("accountLogging");

                        List<Book> bookList= bookService.findAll();


                        request.setAttribute("bookList",bookList);
                        request.setAttribute("student",student1);


                        RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/studentHomePage.jsp");
                        requestDispatcher.forward(request,response);
                    }else if (account.getRole().getId() == 2 && account.getStatus().getId() == 1){
                        List<Librarian> librarianList= librarianService.findAll();
                        Librarian librarian= new Librarian();
                        for (Librarian l: librarianList){
                            if (l.getAccount().getId() == account.getId()){
                                librarian= l;
                                break;
                            }
                        }
                        HttpSession session1= request.getSession();
                        Account account1= (Account) session1.getAttribute("accountLogging");


                        List<Book> bookList= bookService.findAll();


                        request.setAttribute("bookList",bookList);
                        request.setAttribute("librarian",librarian);


                        RequestDispatcher requestDispatcher= request.getRequestDispatcher("librarian/librarianHomePage.jsp");
                        requestDispatcher.forward(request,response);
                    }
                }else {
                    request.setAttribute("message","Wrong password! Please try again");
                    RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/login.jsp");
                    requestDispatcher.forward(request,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showCreateAccountForm(HttpServletRequest request,HttpServletResponse response) {
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/createAccountForm.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createAccount(HttpServletRequest request,HttpServletResponse response){
        String fullName= request.getParameter("fullName");
        String username= request.getParameter("username");
        String password= request.getParameter("password");
        String checkPassword=request.getParameter("checkPassword");
        Status status= statusService.findById(1);
        Role role= roleService.findById(Integer.parseInt(request.getParameter("role")));

        if (accountService.findByUserName(username) != null){
            request.setAttribute("message","Username is already used! Please try again");
            request.setAttribute("fullName",fullName);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/createAccountForm.jsp");
            try {
                requestDispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!password.equals(checkPassword)){
            request.setAttribute("message","Repeat the wrong password! Please try again");
            request.setAttribute("fullName",fullName);
            RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/createAccountForm.jsp");
            try {
                requestDispatcher.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            accountService.add(new Account(username,password,fullName,status,role));

            Account account= accountService.findByUserName(username);
            int id= account.getId();
            if (role.getId() == 1){
                request.setAttribute("account_id",id);
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("student/createStudent.jsp");
                try {
                    requestDispatcher.forward(request,response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                request.setAttribute("account_id",id);
                RequestDispatcher requestDispatcher= request.getRequestDispatcher("librarian/createLibrarian.jsp");
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

    private void createStudent(HttpServletRequest request, HttpServletResponse response){
        String name= request.getParameter("studentName");
        String className= request.getParameter("className");
        int id= Integer.parseInt(request.getParameter("accountId"));
        Account account= accountService.findById(id);
        Student student= new Student(name,className,account);

        studentService.add(student);
        List<Student> studentList= studentService.findAll();
        Student student1= new Student();
        for (Student st: studentList){
            if (st.getAccount().getId() == account.getId()){
                student1= st;
                break;
            }
        }



        request.setAttribute("student",student1);
        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/login.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createLibrarian(HttpServletRequest request, HttpServletResponse response){
        String name= request.getParameter("librarianName");
        int id= Integer.parseInt(request.getParameter("accountId"));
        Account account= accountService.findById(id);
        Librarian librarian= new Librarian(name,account);

        librarianService.add(librarian);
        List<Librarian> librarianList= librarianService.findAll();
        Librarian librarian1= new Librarian();
        for (Librarian l: librarianList){
            if (l.getAccount().getId() == account.getId()) {
                librarian1= l;
                break;
            }
        }

        request.setAttribute("librarian",librarian1);

        RequestDispatcher requestDispatcher= request.getRequestDispatcher("account/login.jsp");
        try {
            requestDispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
