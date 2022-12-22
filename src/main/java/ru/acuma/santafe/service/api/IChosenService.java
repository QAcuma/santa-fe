package ru.acuma.santafe.service.api;

import ru.acuma.santafe.model.domain.Santa;

public interface IChosenService {
    Santa findVictim(Long id, Long chatId);
}
