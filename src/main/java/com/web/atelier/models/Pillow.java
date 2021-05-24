package com.web.atelier.models;

import javax.persistence.*;

@Entity
public class Pillow {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private int width;
    private int height;
    private float price;


    public Pillow(int width, int height, float price) {
        this.width = width;
        this.height = height;
        this.price = price;
    }

    public Pillow() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
