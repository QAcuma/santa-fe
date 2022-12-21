package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.acuma.santafe.bot.SantaFeBot;
import ru.acuma.santafe.service.api.IExecuteService;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteService implements IExecuteService {

    private final SantaFeBot santaFeBot;
    private final ExecutorService syncExecutors = Executors.newFixedThreadPool(4);

    @Override
    @SneakyThrows
    public <T extends Serializable, M extends BotApiMethod<T>> T execute(M method) {
        return syncExecutors.submit(() -> doExecute(method)).get();
    }

    @Override
    public <T extends Serializable, M extends BotApiMethod<T>> T executeApi(M method) {
        T result = null;
        try {
            result = santaFeBot.executeApiMethod(method);
        } catch (TelegramApiException exception) {
            log.warn(exception.getMessage());
        }

        return result;
    }

    @SneakyThrows
    private <T extends Serializable, M extends BotApiMethod<T>> T doExecute(M method) {
        try {
            return santaFeBot.execute(method);
        } catch (TelegramApiRequestException e) {
            e.getMessage();
        }

        return null;
    }

}
