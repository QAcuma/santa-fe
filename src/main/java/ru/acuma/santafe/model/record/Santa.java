package ru.acuma.santafe.model.record;

import org.springframework.data.annotation.Id;

public record Santa (
        @Id
        Long telegramId,
        String login,
        String name,
        String surname
) {

    @Override
    public String toString() {
        var fullName = name + " " + surname;

        return fullName.isBlank()
                ? login
                : fullName;
    }
}
