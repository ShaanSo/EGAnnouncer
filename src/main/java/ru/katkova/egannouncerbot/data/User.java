package ru.katkova.egannouncerbot.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TELEGRAM_USERS")
public class User {
    @Id
    @Column(name = "chatid")
    private Long chatId;

    public User()
    { }

    public User(Long chatId) {
        this.chatId = chatId;
    }
}
