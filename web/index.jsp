<%--
  Created by IntelliJ IDEA.
  User: cnxqin
  Date: 2020/06/17
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    request.getRequestDispatcher("/message/getAll").forward(request,response);
%>

</body>
</html>
