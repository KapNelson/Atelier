package com.web.atelier.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String login;
    private String password;
    private String role;
    private Boolean enabled;

    public User(String login, String password, String role, boolean enabled) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public String isEnabled() {
        if(enabled)
            return "Активний";
        else
            return "Не активний";
    }

    public String banUser() {
        if(enabled)
            return "Заблокувати";
        else
            return "Розблокувати";
    }
}
