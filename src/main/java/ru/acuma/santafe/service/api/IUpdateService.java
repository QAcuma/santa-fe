package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IUpdateService {
    void handle(Update update);
}
