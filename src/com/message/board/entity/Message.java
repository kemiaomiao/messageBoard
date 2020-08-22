package com.message.board.entity;

import java.util.Date;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 15:39
 */
public class Message {

    private Long id;
    private String title;
    private Long authorId; //发布用户
    private String authorName; //冗余发布用户名称
    private String content;
    private Integer resolve = 0; // 是否解决：0-未解决；1-已解决
    private Integer status = 0; // 状态：0-保存；1-正常发布；2-已删除
    private String publishDate;
    private String updateDate;

    public Message() {
    }

    public Message(Long id, String title, Long authorId, String authorName,
                   String content, Integer resolve, Integer status, String publishDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.resolve = resolve;
        this.status = status;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getResolve() {
        return resolve;
    }

    public void setResolve(Integer resolve) {
        this.resolve = resolve;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
