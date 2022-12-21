package ru.acuma.santafe.service.api;

import ru.acuma.santafe.model.domain.Santa;

public interface IChosenService {
    Santa lookup(Long id, Long chatId);
}
