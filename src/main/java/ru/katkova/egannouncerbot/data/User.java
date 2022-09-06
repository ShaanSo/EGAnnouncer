package ru.katkova.egannouncerbot.data;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "TELEGRAM_USERS")
public class User {
    @Id
    @Column(name = "chatid")
    private String chatId;

    public User()
    { }

    public User(String chatId) {
        this.chatId = chatId;
    }
}
