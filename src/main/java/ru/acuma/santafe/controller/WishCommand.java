package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommands;
import ru.acuma.santafe.service.impl.WishService;

@Slf4j
@Component
public class WishCommand extends BaseBotCommand {

    private WishService wishService;

    @Autowired
    public void setWishService(@Lazy WishService wishService) {
        this.wishService = wishService;
    }

    public WishCommand() {
        super(PrivateCommands.WISH.getCommand(), "Show victim");
    }

    @Override
    public void execute(Message message) {
        wishService.remindUserWishes(message.getFrom().getId(), message.getChatId());
    }

}
