package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IWishService;

@Slf4j
@Component
public class RemindCommand extends BaseBotCommand {

    private final IWishService wishService;
    private final ISantaService santaService;

    public RemindCommand(@Lazy IWishService wishService, @Lazy ISantaService santaService) {
        super(PrivateCommand.REMIND.getCommand(), "Show victim");
        this.wishService = wishService;
        this.santaService = santaService;
    }

    @Override
    public void execute(Message message) {
        var santa = santaService.findByTelegramId(message.getFrom().getId());
        wishService.sendWish(santa, message.getChatId());
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
