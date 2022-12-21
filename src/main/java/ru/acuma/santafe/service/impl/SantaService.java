package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.acuma.santafe.model.record.Santa;
import ru.acuma.santafe.repository.SantaRepository;
import ru.acuma.santafe.service.api.ISantaService;

@Service
@RequiredArgsConstructor
public class SantaService implements ISantaService {

    private final SantaRepository santaRepository;

    @Override
    public void checkInSanta(User user) {
        var santa = new Santa(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName()
        );

        santaRepository.save(santa);
    }

    @Override
    public boolean existById(Long telegramId) {
        return santaRepository.existsById(telegramId);
    }

    @Override
    public Santa findByTelegramId(Long telegramId) {
        return santaRepository.findByTelegramId(telegramId);
    }


}
