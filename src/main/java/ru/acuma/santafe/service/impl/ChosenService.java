package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.acuma.santafe.model.domain.Santa;
import ru.acuma.santafe.repository.SantaRepository;
import ru.acuma.santafe.service.api.IChosenService;

@Service
@RequiredArgsConstructor
public class ChosenService implements IChosenService {

    private final SantaRepository santaRepository;

    @Override
    public Santa lookup(Long id, Long chatId) {
        var user = santaRepository.findByTelegramId(id);

        return santaRepository.findByTelegramId(user.getVictimTelegramId());
    }
}
