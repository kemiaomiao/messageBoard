package com.message.board.entity;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/19 22:39
 */
public class ReplayMesssage {

    private Long id;
    private String content;
    private String publishDate;

    private Long messageId; //1-n

    private Long authorId; //发布用户
    private String authorName; //冗余发布用户名称

}
