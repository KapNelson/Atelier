package com.web.atelier.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String text;
    private LocalDate publicationDate;

    public News(String title, String text, LocalDate publicationDate) {
        this.title = title;
        this.text = text;
        this.publicationDate = publicationDate;
    }

    public String getShortText() {
        if (text.length() <= 100) {
            return text;
        } else {
            return text.substring(0, 100) + "...";
        }
    }

    public String getPublicationDateInString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return publicationDate.format(formatters);
    }

    public String getTextWithIndentation() {
        return text.replace("\n", "<br>");
    }
}
