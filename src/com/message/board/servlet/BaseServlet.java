package com.message.board.servlet;

import com.alibaba.fastjson.JSONObject;
import com.message.board.common.ResultInfo;
import com.message.board.dao.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 17:50
 */
public abstract class BaseServlet extends HttpServlet {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo result = null;
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String traceId = getTraceId();
        //获取绝对路径
        String url = request.getRequestURI();
        try {
            //获取相对路径
            String contextPath = request.getContextPath();
            url = url.replaceAll(contextPath,"").replaceAll("/+","/");
            System.out.printf("[%s] [%s] [%s]\n",traceId,df.format(new Date()),url);
            result = doDispatch(url.substring(url.lastIndexOf("/") + 1), request, response); //实现请求逻辑

        }catch (Exception e){
            result = new ResultInfo();
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        try {
            if(result == null){
                response.sendRedirect("/");
                return;
            }
            if(result.isJson()){
                String json = JSONObject.toJSONString(result);
                System.out.printf("[%s] [%s] [%s] => [%s]\n",traceId,df.format(new Date()),url,json);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(json); //序列化返回
            }else {
                request.getRequestDispatcher(result != null ? result.getForwordUrl() : "/").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract ResultInfo doDispatch(String url, HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DataSource.getConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTraceId(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,20);
    }
}
