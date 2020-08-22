package com.message.board.service;

import com.message.board.common.ResultInfo;
import com.message.board.dao.UserDao;
import com.message.board.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 17:18
 */
public class UserService {

    public ResultInfo<User> login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResultInfo<User> resultInfo= new ResultInfo<>();
        try {
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            UserDao userDao = new UserDao();
            User user = userDao.selectByAccountAndPwd(account,password);
            if(user != null){
                resultInfo.setData(user);
                resultInfo.setSuccess(true);
                resultInfo.setMsg("登录成功");
                request.getSession().setAttribute("user",user);
                request.setAttribute("user",user);
                request.setAttribute("account",account);
                request.setAttribute("aaa","aaa");
            }else{
                resultInfo.setMsg("用户名或密码不正确");
            }
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }

    public ResultInfo<User> logout(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<User> resultInfo= new ResultInfo<>();
        request.getSession().removeAttribute("user");
        resultInfo.setSuccess(true);
        resultInfo.setMsg("登出成功");
        return resultInfo;
    }

    public ResultInfo<User> register(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<User> resultInfo= new ResultInfo<>();
        try {
            String account = request.getParameter("account");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            UserDao userDao = new UserDao();

            User user = userDao.selectByAccountAndPwd(account,null);
            if(user != null){
                resultInfo.setSuccess(false);
                resultInfo.setMsg("该账号已存在，请重新输入！");
                return resultInfo;
            }
            user = new User();
            user.setName(name);
            user.setAccount(account);
            user.setPassword(password);
            user.setRole(0);
            int result = userDao.save(user);
            if(result == 1){
                resultInfo.setSuccess(true);
                resultInfo.setMsg("注册成功，将跳转到主页");
                request.getSession().setAttribute("user",user);
            }else{
                resultInfo.setMsg("注册失败");
            }
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }
}
