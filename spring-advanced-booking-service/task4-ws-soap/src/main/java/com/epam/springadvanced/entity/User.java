package com.epam.springadvanced.entity;

import java.time.LocalDate;
import java.util.List;

public class User {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private String password;
    private List<Role> roles;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, LocalDate birthday) {
        this(name, email);
        this.birthday = birthday;
    }

    public User(long id, String name, String email, LocalDate birthday) {
        this(name, email, birthday);
        this.id = id;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
