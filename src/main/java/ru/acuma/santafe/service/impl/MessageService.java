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
    public String wishAlreadyExists(String userName) {
        return "Санте уже известно об этом пожелании пользователя %s".formatted(userName);
    }

    @Override
    public String remindWishMessage(String userName) {
        return "Пожелания %s на %s год".formatted(userName, LocalDateTime.now().getYear());
    }

    @Override
    public String missingWishMessage(String userName) {
        return "Пока что у меня нет пожеланий пользователя %s ".formatted(userName);
    }

    @Override
    public String startPrivateChatMessage(String userName) {
        return """
                Санта подобрал счастливчика для тебя! Жми кнопку под сообщением и скорее узнавай о подарке!.
                                
                Подарок нужно упаковать и крупно написать на нем для кого он.
                                
                Веселье начинается!
                """;
    }
}
