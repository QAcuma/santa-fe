package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.acuma.santafe.model.domain.Santa;

import java.util.List;

public interface ISantaService {

    void checkInSanta(User user);

    Santa findByTelegramId(Long telegramId);

    List<Santa> findAllActiveSantas(List<Long> santaIds);

    void saveAll(List<Santa> santas);

    void save(Santa santa);

    void flushSantas();
}
