package com.learn.springSecurity.springsecurityJPA.model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
    @Id
    private String id;
    @Column(name = "active")
    private String active;
    @Column(name="password")
    private String password;
    @Column(name="roles")
    private String roles;
    @Column(name = "user_name")
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
