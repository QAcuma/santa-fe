package ru.acuma.santafe.service.impl;

import org.springframework.stereotype.Service;
import ru.acuma.santafe.service.api.IMessageService;

import java.time.LocalDateTime;

@Service
public class MessageService implements IMessageService {

    @Override
    public String acceptedWishMessage(String userName) {
        return "Пожелание для пользователя %s сохранено".formatted(userName);
    }

    @Override
    public String remindWishMessage(String userName) {
        return "Пожелания %s на %s год".formatted(userName, LocalDateTime.now().getYear());
    }

    @Override
    public String missingWishMessage(String userName) {
        return "Пока что у меня нет пожеланий пользователя %s ".formatted(userName);
    }

}
