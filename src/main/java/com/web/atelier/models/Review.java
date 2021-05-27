package com.web.atelier.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String text;
    private Boolean verified;

    public Review(String name, String phone, String text) {
        this.name = name;
        this.phone = phone;
        this.text = text;
        this.verified = false;
    }

    public String isVerified() {
        if(verified)
            return "Підтверджено";
        else
            return "Не підтверджено";
    }
}
