package ru.acuma.santafe.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.acuma.santafe.controller.BaseBotCommand;
import ru.acuma.santafe.service.impl.SantaService;
import ru.acuma.santafe.service.impl.UpdateService;

import java.io.Serializable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SantaFeBot extends TelegramLongPollingCommandBot {

    @Value("${telegram.bot.name}")
    public String username;

    @Value("${telegram.bot.token}")
    public String token;

    private final List<BaseBotCommand> commands;
    private final @Lazy UpdateService updateService;

    @PostConstruct
    private void init() {
        commands.forEach(this::register);
        connect();
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        updateService.handle(update);
    }

    public final <T extends Serializable, M extends BotApiMethod<T>> T executeApiMethod(M method) throws TelegramApiException {
        return super.execute(method);
    }


    @SneakyThrows
    public void connect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

}
