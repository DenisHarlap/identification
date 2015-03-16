<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 27.02.15
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Login</title>
  <style>
    p{
      font-family:  'Times New Roman', Times, serif;
      font-size: 15pt;
    }
    h3{
      font-family:  'Times New Roman', Times, serif;
      font-size: 17pt;
      color: red;
    }
  </style>
</head>
<body bgcolor="#008b8b">
<h2 align="center"><b>Please, filling next fields:</b></h2>
<br><h3 align="center"><b>${errorMessage}</b></h3>
<br>
<br>
<form name="LoginForm" action="/Login_servlet">
  <table width="100%" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" width="45%"><p><b>Username:</b></p></td>
      <td><input type="text" size="25" maxlength="50" name="username"
                 value="${param.username}"></td>
    </tr>
    <tr>
      <td><p align="right"><b>Password:</b></p></td>
      <td><input type="password" size="25" maxlength="50" name="password"
                 value=""></td>
    </tr>
    <tr><td></td><td></td></tr>
    <tr>
      <td></td>
      <td> <input type="submit" name="button" value="Login"></td>
    </tr>
  </table>
</form>
<c:if test="${not empty textMessage}">
  <c:if test="${empty errorMessage}">
    <jsp:forward page="/Main.jsp"/>
  </c:if>
</c:if>
</body>
</html>
