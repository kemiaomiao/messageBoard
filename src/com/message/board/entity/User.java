package com.message.board.entity;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 15:37
 */
public class User {

    private Long id;
    private String account;
    private String password;
    private String name;
    private Integer role;//0-管理员；1-普通用户

    public User() {
    }

    public User(Long id, String account, String password, String name, Integer role) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
