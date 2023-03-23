package com.example.meusgastos.dto.user;

import java.util.Date;

public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String avatar;

    private Date inative_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getInative_at() {
        return inative_at;
    }

    public void setInative_at(Date inative_at) {
        this.inative_at = inative_at;
    }
}
