package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.impl.ChosenService;
import ru.acuma.santafe.service.impl.SantaService;
import ru.acuma.santafe.service.impl.WishService;

@Slf4j
@Component
public class ChosenCommand extends BaseBotCommand {

    private ChosenService chosenService;
    private SantaService santaService;
    private WishService wishService;

    public ChosenCommand() {
        super(PrivateCommand.CHOSEN.getCommand(), "Display chosen santa");
    }

    @Autowired
    public void setWishService(@Lazy ChosenService chosenService, @Lazy WishService wishService, @Lazy SantaService santaService) {
        this.chosenService = chosenService;
        this.wishService = wishService;
        this.santaService = santaService;
    }

    @Override
    public void execute(Message message) {
        var victim = chosenService.lookup(message.getFrom().getId(), message.getChatId());
        wishService.remindUserWishes(victim.getVictimTelegramId(), message.getChatId());
        victim.setKnowVictim(Boolean.TRUE);
        santaService.save(victim);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
