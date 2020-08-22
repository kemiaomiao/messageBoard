<%--
  Created by IntelliJ IDEA.
  User: cnxqin
  Date: 2020/06/14
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset=utf-8>
    <title>留言板</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#login").click(function(){
                var username = $(":input[name='acount']").val();
                var password = $(":input[name='password']").val();
                username = $.trim(username);
                password = $.trim(password);
                if(username == "" || password == ""){
                    alert("用户名或密码不能为空！");
                }else{
                    args = {"account":username,"password":password};
                    $.post("user/login",args,function(data){
                        result = JSON.parse(data);
                        alert(result.msg);
                        if(result.success){
                            window.location.href = "/";
                        }
                    });
                }
            });

            $("#regis").click(function(){
                window.location.href = "/register.jsp";
            });

            $("#logout").click(function(){
                $.post("user/logout",null,function(data){
                    result = JSON.parse(data);
                    alert(result.msg);
                    if(result.success){
                        window.location.href = "/";
                    }
                });
            });
            $("#publish").click(function(){
                if($("#loginUser") == null || $("#loginUser").html() == null ){
                    alert("权限不足，请先登录");
                }else{
                    $("#viewMessgae").hide();
                    $("#editMessgae").hide();
                    $("#publishMessgae").show();
                }
                return false;
            });
            $("#edit").click(function(){
                if($("#loginUser") == null || $("#loginUser").html() == null ){
                    alert("权限不足，请先登录");
                }else {
                    $("#viewMessgae").hide();
                    $("#editMessgae").show();
                    $("#publishMessgae").hide();
                }
                return false;
            });
            $("input[name='delete']").click(function(){
                args = {"id":$(this).attr("id")}; // attribute
                $.post("message/deleteById",args,function(data){
                    result = JSON.parse(data);
                    alert(result.msg);
                    if(result.success){
                        // $(this).parent().parent().remove();
                        window.location.href = "/";
                    }
                });
            });

            $("label[name='title']").click(function(){
                var id = $(this).attr("id");
                args = {"id":id.replace('title','')};
                $.post("message/getById",args,function(data){
                    result = JSON.parse(data);
                    if(result.success){
                        //更新
                        $("#detailTitle").html(result.data.title);
                        $("#detailAuthor").html(result.data.authorName);
                        $("#detailDate").html(result.data.publishDate);
                        $("#detailContent").html(result.data.content);
                        //修改
                        $("#editTitle").html(result.data.title);
                        $("#editAuthor").html(result.data.authorName);
                        $("#editDate").html(result.data.publishDate);
                        $("#editContent").html(result.data.content);
                        $("#editId").val(result.data.id);
                    }
                });
            });

            $("#publishImg").click(function(){
                var title = $("#publishTitle").val();
                var content = $("#publishContent").val();
                if(title == "" || content == ""){
                    alert("留言标题和内容都不能为空");
                    return;
                }
                var args = {"title":title,"content":content};
                $.post("message/publish",args,function(data){
                    result = JSON.parse(data);
                    if(result.success){
                        alert("发布成功！");
                        //更新
                        window.location.href = "/";
                    }
                });
            });

            $("#editImg").click(function(){
                var content = $("#editContent").val();
                var id = $("#editId").val();
                if( content == ""){
                    alert("留言内容不能为空");
                    return;
                }
                var args = {"id":id,"content":content};
                $.post("message/publish",args,function(data){
                    result = JSON.parse(data);
                    alert(result.msg);
                    if(result.success){
                        //更新
                        window.location.href = "/";
                    }
                });
            });
        })
    </script>
</head>
<body>
<div class="topbg">
        <c:if test="${sessionScope.user.name == null}">
            <div style="float: left; margin-top: 10px; padding-left: 400px;">
            用户名：
            <input id="userid" type="" name="acount">
            密码：
            <input id="userpswd" type="password" name="password">
                <button id="login" class="login">登录</button>
                <button id="regis" class="login">注册</button>
        </c:if>
        <c:if test="${sessionScope.user.name != null}">
            <div style="float: left; margin-top: 10px; padding-left: 800px;">
            欢迎您：<label id="loginUser">${sessionScope.user.name}</label>
                <button id="logout" class="login">注销</button>
        </c:if>
    </div>
    <div class="block1">
        <div class="liuyan1"><a id="view" href="/">查看留言</a></div>
        <div class="liuyan2"><a id="publish" href="publish.jsp">发布留言</a></div>
        <div class="liuyan3"><a id="edit" href="edit.jsp">修改留言</a></div>
    </div>
</div>
<div>
    <div class="lynr">
        <img src="images/lynr.jpg">
        <table width="100%">
            <c:forEach items="${list}" var="message">
                <tr>
                    <td><div class="dian"></div><label hidden="true">${message.id}</label></td>
                    <%--<td align="left">${message.title}</td>--%>
                    <td align="left"><label name="title" id="title${message.id}" >${message.title}</label></td>
                    <td width="100px">${message.publishDate}</td>
                    <c:if test="${sessionScope.user != null &&
                        (sessionScope.user.role == 1 || message.authorId==sessionScope.user.id)}">
                        <td width="50px">
                            <input type="button" name="delete" id="${message.id}" value="删除"/>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <div class="block2">
            <div class="paging1"><a href="#">上一页</a></div>
            <div class="paging2"><a href="#">下一页</a></div>
            <div class="paging3"><a href="#">首页</a></div>
        </div>
    </div>
    <div class="bigblock">
        <div id="viewMessgae" class="ckly">
            <img src="images/03.jpg">
            <div class="contect">
                <p><b>主题：</b><label id="detailTitle">${list[0].title}</label></p>
                <p><b>作者：</b><label id="detailAuthor">${list[0].authorName}</label></p>
                <p><b>日期：</b><label id="detailDate">${list[0].publishDate}</label></p>
                <p><b>内容：</b>
                <div class="nr">
                    <label id="detailContent">${list[0].content}
                    </label>
                </div>
                </p>
            </div>
        </div>

        <div id="publishMessgae" class="ckly" hidden="hidden">
            <img src="images/4.jpg">
            <ul>
                <li>主题：</li>
                <div><input id="publishTitle" type="text" name="title" style="width: 310px"></div>
                <li>内容：</li>
                <div><textarea id="publishContent" style="height: 200px;width: 310px" name="content"></textarea></div>
            </ul>
            <div style="float: right;margin-right: 20px"><img id="publishImg" src="images/5.jpg"></div>
        </div>

        <div id="editMessgae" class="ckly" hidden="hidden">
            <img src="images/6.jpg">
            <div class="contect">
                <input id="editId" hidden="hidden"/>
                <p><b>主题：</b><label id="editTitle">${list[0].title}</label></p>
                <p><b>作者：</b><label id="editAuthor">${list[0].authorName}</label></p>
                <p><b>日期：</b><label id="editDate">${list[0].publishDate}</label></p>
                <p><b>内容：</b>
                <div class="nr">
                    <textarea id="editContent" style="height: 100px;width: 250px" >${list[0].content}</textarea>
                </div>
                <div style="float: right;margin-right: 20px"><img id="editImg" src="images/7.jpg"></div>
            </div>
        </div>
    </div>
</div>
<div class="bottom">
    <img src="images/02.jpg">
</div>
</body>
</html>
