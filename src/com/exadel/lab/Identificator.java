package com.exadel.lab;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;

/**
 * Created by Denis on 27.02.15.
 */
@WebServlet(name = "Identificator",urlPatterns = "/Identificator")
public class Identificator extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String param = request.getParameter("user");
        if (param == null){
            PrintWriter out = response.getWriter();
            out.write("<html><head>\r\n");
            out.write("<title>Error</title>\r\n");
            out.write("</head>\r\n");
            out.write("<body bgcolor=red>\r\n");
            out.write("<h1 align = center ><b>Please, select action!!!</b></h1>\r\n");
            out.write("<p align = center><a href = ../index.html>Start Page</a></p>\r\n");
            out.write("</body></html>\r\n");
            out.close();
        }
        else if (param.equals("Registration")) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registration.jsp");
            dispatcher.forward(request,response);
        }
        else if (param.equals("Login")){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
            dispatcher.forward(request,response);
        }
    }

}
