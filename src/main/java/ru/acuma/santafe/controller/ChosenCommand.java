package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IChosenService;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IWishService;

@Slf4j
@Component
public class ChosenCommand extends BaseBotCommand {

    private final IChosenService chosenService;
    private final ISantaService santaService;
    private final IWishService wishService;
    private final IExecuteService executeService;

    public ChosenCommand(@Lazy IChosenService chosenService, @Lazy ISantaService santaService, @Lazy IWishService wishService, @Lazy IExecuteService executeService) {
        super(PrivateCommand.CHOSEN.getCommand(), "Display chosen santa");
        this.chosenService = chosenService;
        this.santaService = santaService;
        this.wishService = wishService;
        this.executeService = executeService;
    }

    @Override
    public void execute(Message message) {
        var victim = chosenService.lookup(message.getFrom().getId(), message.getChatId());
        if (victim == null) {
            var text = "Время узнать счастливчика ещё не пришло!";
            var response = new SendMessage(String.valueOf(message.getChatId()), text);
            executeService.execute(response);

            return;
        }
        wishService.remindUserWishes(victim.getVictimTelegramId(), message.getChatId());
        var santa = santaService.findByTelegramId(message.getFrom().getId());
        santa.setKnowVictim(Boolean.TRUE);
        santaService.save(santa);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
