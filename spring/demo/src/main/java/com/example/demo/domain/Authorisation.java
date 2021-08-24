package com.example.demo.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth")
public class Authorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private boolean authrorised;

    @Column
    private Timestamp timestamp;

    @OneToOne
    private User user;

     public Authorisation(Long id, String code, boolean authrorised, User user, Timestamp timestamp) {
        this.id = id;
        this.code = code;
        this.authrorised = authrorised;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Authorisation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAuthrorised() {
        return authrorised;
    }

    public void setAuthrorised(boolean authrorised) {
        this.authrorised = authrorised;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
