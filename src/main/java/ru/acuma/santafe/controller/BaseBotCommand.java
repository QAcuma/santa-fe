package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.acuma.santafe.service.api.ISantaService;

@Slf4j
public abstract class BaseBotCommand extends BotCommand {

    private ISantaService santaService;

    public BaseBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Autowired
    public void setSantaService(@Lazy ISantaService santaService) {
        this.santaService = santaService;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        santaService.checkInSanta(message.getFrom());
        if (denyInGroups() && message.isGroupMessage()) {
            return;
        }

        execute(message);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.warn("BotCommand execute not implemented");
    }

    public abstract void execute(Message message);

    protected abstract Boolean denyInGroups();

}
