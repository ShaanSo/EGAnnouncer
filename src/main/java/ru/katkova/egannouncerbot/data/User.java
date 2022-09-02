package ru.katkova.egannouncerbot.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String chatId;

    public User()
    { }

    public User(String chatId) {
        this.chatId = chatId;
    }
}
