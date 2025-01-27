package com.register.registers.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GenerationType;


@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique = true)
    private String email;
    @Column(name = "password_hash")
    @JsonIgnore
    private String passwordHash; 

    public long getUserId() {
        return userId;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    public Users() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return passwordHash;
    }

    public void setPassword_hash(String password_hash) {
        this.passwordHash = password_hash;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
