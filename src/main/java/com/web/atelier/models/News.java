package com.web.atelier.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private String text;
    private LocalDate publicationDate;
    private String imageType;

    public News(String title, String text, LocalDate publicationDate, String imageType) {
        this.title = title;
        this.text = text;
        this.publicationDate = publicationDate;
        this.imageType = imageType;
    }

    public String getShortText() {
        if(text.length() <= 100) {
            return text;
        } else {
            return text.substring(0, 100) + "...";
        }
    }

    public String getPublicationDateInString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = publicationDate.format(formatters);
        return date;
    }

    public String getTextWithIndentation() {
        return text.replace("\n", "<br>");
    }

    public String getImagePath(String id, String imageType) {
        String filePath = String.format("src/main/resources/static/img/news/%s.%s", id, imageType);
        File file = new File(filePath);

        if(file.exists()) {
            return String.format("img/news/%s.%s", id, imageType);
        } else {
            return "img/news/news.jpg";
        }
    }
}
