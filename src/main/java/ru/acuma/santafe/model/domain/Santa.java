package ru.acuma.santafe.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
public class Santa {

    @Id
    private Long telegramId;
    private Long victimTelegramId;
    private Boolean knowVictim;
    private String chatId;
    private String login;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        var fullName = firstName + " " + lastName;

        return fullName.isBlank()
                ? login
                : fullName;
    }
}
