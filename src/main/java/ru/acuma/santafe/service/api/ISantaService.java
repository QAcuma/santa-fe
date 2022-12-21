package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.acuma.santafe.model.record.Santa;

public interface ISantaService {
    void checkInSanta(User user);

    boolean existById(Long telegramId);

    Santa findByTelegramId(Long telegramId);
}
