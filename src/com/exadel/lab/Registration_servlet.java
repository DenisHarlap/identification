package com.exadel.lab;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Denis on 05.03.15.
 */

@WebServlet(name = "Registration_servlet",urlPatterns = "/Registration_servlet")
public class Registration_servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        registrationUser(request, response);
    }

    private void registrationUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        String errorMessage = "",textMessage = "";
        String text_user = "Welcome, user. Your identification is successful.";
        String text_admin = "Welcome, user. Your identification is successful. Your status is superAdmin";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passConf = request.getParameter("passConf");
        String superAdmin = request.getParameter("checkAdmin");
        byte admin = 0;
        if (superAdmin == null)
            admin = 0;
        else if(superAdmin.equals("superAdmin"))
            admin = 1;
        if (username.isEmpty())
            errorMessage = "Error: field username is empty.";
        else if (password.isEmpty() || passConf.isEmpty())
            errorMessage = "Error: fields password or confirm password is empty.";
        else if (password.length() <= 5)
            errorMessage = "Error: password must contain more 5 symbols.";
        else if (password.equals(username))
            errorMessage = "Error: password equals field username";
        else if (!password.equals(passConf))
            errorMessage = "Error: password and confirm password is not equal.";
        else {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/user_database", "root", "doping");
                String query;
                query = "SELECT USERNAME FROM USERS WHERE USERNAME = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,username);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    errorMessage = "Error: your login already exists";
                }
                else {
                    query = "INSERT INTO USERS VALUES (DEFAULT,?,?,?,?);";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setByte(3, admin);
                    java.util.Date currentDate = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                    preparedStatement.setDate(4, sqlDate);
                    preparedStatement.executeUpdate();
                    if (admin == 1) {
                        query = "INSERT INTO TEXTMESSAGE VALUES ((SELECT USERID FROM USERS WHERE USERNAME = ?),?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,username);
                        preparedStatement.setString(2,text_admin);
                        preparedStatement.executeUpdate();
                        textMessage = text_admin;
                    }
                    else{
                        query = "INSERT INTO TEXTMESSAGE VALUES ((SELECT USERID FROM USERS WHERE USERNAME = ?),?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1,username);
                        preparedStatement.setString(2,text_user);
                        preparedStatement.executeUpdate();
                        textMessage = text_user;
                    }
                }

            } catch (Exception e) {
                errorMessage = "Error on the connection to database: " + e;
            } finally {
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (preparedStatement != null)
                        preparedStatement.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e){
                    errorMessage = "Error on close connection to database: "+ e;
                }
            }
        }
        request.setAttribute("errorMessage",errorMessage);
        request.setAttribute("textMessage",textMessage);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Registration.jsp");
        dispatcher.forward(request,response);
    }
}
