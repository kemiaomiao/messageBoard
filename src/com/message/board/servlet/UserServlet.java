package com.message.board.servlet;

import com.message.board.common.ResultInfo;
import com.message.board.entity.User;
import com.message.board.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 17:07
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends BaseServlet {

    @Override
    protected ResultInfo<User> doDispatch(String url, HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserService userService = new UserService();
        String name = null;
        switch (url) {
            case "login" : return userService.login(request, response);
            case "logout" : return userService.logout(request,response);
            case "register" : return userService.register(request,response);
            case "get" : System.out.println("收到 user/get 的请求");
                name = request.getParameter("name");
                System.out.println("请求参数name = " + name);
                break;
            case "post":System.out.println("收到 user/post 的请求 ");
                name = request.getParameter("name");
                System.out.println("请求参数name = " + name);
                break;
        }
        return null;
    }


}
