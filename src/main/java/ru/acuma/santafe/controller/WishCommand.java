package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.impl.WishService;

@Slf4j
@Component
public class WishCommand extends BaseBotCommand {

    private WishService wishService;

    public WishCommand() {
        super(PrivateCommand.WISH.getCommand(), "Show victim");
    }

    @Autowired
    public void setWishService(@Lazy WishService wishService) {
        this.wishService = wishService;
    }

    @Override
    public void execute(Message message) {
        wishService.remindUserWishes(message.getFrom().getId(), message.getChatId());
    }

}
