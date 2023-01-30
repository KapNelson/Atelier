package com.web.atelier.models;

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
public class Pillow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer width;
    private Integer height;
    private Float price;

    public Pillow(int width, int height, float price) {
        this.width = width;
        this.height = height;
        this.price = price;
    }
}
