package com.web.atelier.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private String text;
    private LocalDate publicationDate;

    public News() {

    }

    public News(String title, String text, LocalDate publicationDate) {
        this.title = title;
        this.text = text;
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getShortText() {
        if(text.length() <= 100) {
            return text;
        } else {
            return text.substring(0, 100) + "...";
        }
    }

    public String getPublicationDateInString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM");
        String date = publicationDate.format(formatters);
        return date;
    }

    public String getTextWithIndentation() {
        return text.replace("\n", "<br>");
    }

}
