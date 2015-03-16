<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 02.03.15
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User's page</title>
</head>
<body bgcolor="#dda0dd">
  <h2 align="center"><b>User: Dear ${param.username}</b></h2>
  <br>
  <br>
  <h3 align="left">${textMessage}</h3>
  <br>
  <br>
  <form name = main action="index.html">
    <table width="100%" align="center">
      <tr>
        <td align="center"><input type="submit" name="exit" value="Exit" size = "30"></td>
      </tr>
    </table>
  </form>
</body>
</html>
