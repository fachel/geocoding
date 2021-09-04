package com.belov.geocoding.geocoding.entity;

import javax.persistence.*;

@Entity
@Table(name = "exceptions")
public class Exception_ {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_")
    private String class_;

    @Column(name = "message_")
    private String message;

    public Exception_() {
    }

    public Exception_(String class_, String message) {
        this.class_ = class_;
        this.message = message;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
