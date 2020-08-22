<%--
  Created by IntelliJ IDEA.
  User: cnxqin
  Date: 2020/06/18
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#submit").click(function () {
                var account = $(":input[name='account']").val();
                var name = $(":input[name='name']").val();
                var password = $(":input[name='password']").val();
                var password1 = $(":input[name='password1']").val();
                account = $.trim(account);
                name = $.trim(name);
                password = $.trim(password);
                password1 = $.trim(password1);
                if(account == "" || name == "" || password == "" || password1 == ""){
                    alert("请填写所有信息！");
                    return false;
                }
                if(password != password1){
                    alert("两次输入密码不一致，请确认");
                    return false;
                }
                args = {"account":account,"password":password,"name":name};
                $.post("user/register",args,function(data){
                    result = JSON.parse(data);
                    alert(result.msg);
                    if(result.success){
                        window.location.href = "/";
                    }
                });
            });
        });
    </script>
</head>
<body>
    <div style="margin-left: 20%">
        <%--<form action="user/register" method="post">--%>
            <table>
                <tr>
                    <td>用户名：</td>
                    <td><input name="account" type="text"/></td>
                </tr>
                <tr>
                    <td>姓名：</td>
                    <td><input name="name" type="text"/></td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input name="password" type="password"></td>
                </tr>
                <tr>
                    <td>再次输入密码：</td>
                    <td><input name="password1" type="password"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input id="submit" name="password" type="submit" value="注册"></td>
                </tr>
            </table>
        <%--</form>--%>
    </div>
</body>
</html>
