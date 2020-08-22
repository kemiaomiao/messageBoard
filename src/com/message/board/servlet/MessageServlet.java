package com.message.board.servlet;

import com.message.board.common.ResultInfo;
import com.message.board.entity.Message;
import com.message.board.service.MessageService;

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
@WebServlet(name = "MessageServlet")
public class MessageServlet extends BaseServlet {
    @Override
    protected ResultInfo<Message> doDispatch(String url, HttpServletRequest request, HttpServletResponse response) {

        MessageService messageService = new MessageService();
        switch (url) {
            case "getAll": return messageService.getAll(request, response);
            case "deleteById": return messageService.deleteById(request, response);
            case "getById": return messageService.getById(request, response);
            case "publish": return messageService.publish(request, response);
        }

        return null;
    }
}
