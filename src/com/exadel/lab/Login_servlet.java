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
@WebServlet(name = "Login_servlet",urlPatterns = "/Login_servlet")
public class Login_servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginUser(request,response);
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        String errorMessage = "",textMessage = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.isEmpty())
            errorMessage = "Error: field username is empty.";
        else if (password.isEmpty())
            errorMessage = "Error: fields password is empty.";
        else {
            Connection connection = null;
            Statement statement = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null,resultAdminFlag = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/user_database", "root", "doping");
                statement = connection.createStatement();
                String query;
                query = "SELECT USERNAME FROM USERS WHERE USERNAME = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,username);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    boolean userExists = false;
                    query = "SELECT ADMIN_FLAG FROM USERS WHERE USERNAME = ? AND PASSWORD = ?;";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    resultSet = preparedStatement.executeQuery();
                    byte adminStatus;
                    if (resultSet.next()){
                        userExists = true;
                        adminStatus = resultSet.getByte(1);
                        if (adminStatus == 1) {
                            query = "SELECT TEXT FROM TEXTMESSAGE WHERE ID = 2;";
                            resultAdminFlag = statement.executeQuery(query);
                        }
                        else{
                            query = "SELECT TEXT FROM TEXTMESSAGE WHERE ID = 1;";
                            resultAdminFlag = statement.executeQuery(query);
                        }
                        if (resultAdminFlag.next())
                            textMessage = resultAdminFlag.getString(1);
                    }
                    if (!userExists)
                        errorMessage = "Error: wrong password.";
                }
                else
                    errorMessage = "Error: your login don't exists. Please, register on our website.";
            } catch (Exception e) {
                errorMessage = "Error on the connection to database: " + e;
            } finally {
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (resultAdminFlag != null)
                        resultAdminFlag.close();
                    if (statement != null)
                        statement.close();
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
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
        dispatcher.forward(request,response);
    }
}
