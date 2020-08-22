package com.message.board.service;

import com.message.board.common.ResultInfo;
import com.message.board.dao.MessageDao;
import com.message.board.entity.Message;
import com.message.board.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 20:03
 */
public class MessageService {
    public ResultInfo<Message> getAll(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<Message> resultInfo= new ResultInfo<>();
        try {
            MessageDao messageDao = new MessageDao();
            List<Message> messageList = messageDao.selectAll(null);
            resultInfo.setSuccess(true);
            resultInfo.setList(messageList);
            resultInfo.setMsg("操作成功");
            resultInfo.setJson(false);
            resultInfo.setForwordUrl("/message.jsp");
            request.setAttribute("list",resultInfo.getList());
//            request.getSession().removeAttribute("list");
//            request.getSession().setAttribute("list",resultInfo.getList());
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }

    public ResultInfo<Message> deleteById(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<Message> resultInfo= new ResultInfo<>();
        try {
            Long id = new Long(request.getParameter("id"));
            MessageDao messageDao = new MessageDao();
            messageDao.delete(id);
            resultInfo.setSuccess(true);
            resultInfo.setMsg("删除成功");
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }

    public ResultInfo<Message> getById(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<Message> resultInfo= new ResultInfo<>();
        try {
            Long id = new Long(request.getParameter("id"));
            MessageDao messageDao = new MessageDao();
            List<Message> messageList = messageDao.selectAll(id);
            if(messageList.size() > 0) {
                resultInfo.setData(messageList.get(0));
                resultInfo.setSuccess(true);
                resultInfo.setMsg("操作成功");
            }
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }

    public ResultInfo<Message> publish(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<Message> resultInfo= new ResultInfo<>();
        try {
            Long id = request.getParameter("id") != null ? new Long(request.getParameter("id")) : null;
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            User user = (User) request.getSession().getAttribute("user");
            MessageDao messageDao = new MessageDao();

            Message message = null;
            if(id != null){
                message = messageDao.select(id);

                //权限校验
                if(user.getId() != message.getAuthorId()){
                    resultInfo.setMsg("操作失败：仅允许更改自己的留言信息！");
                    return resultInfo;
                }

            }else{
                message = new Message();
                message.setAuthorId(user.getId());
                message.setAuthorName(user.getName());
            }
            message.setId(id);
            message.setTitle(title);
            message.setContent(content);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            message.setPublishDate(df.format(new Date()));

            if(id != null) {
                messageDao.update(message);
            }else{
                messageDao.save(message);
            }
            resultInfo.setSuccess(true);
            resultInfo.setMsg("操作成功");
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return resultInfo;
    }
}
