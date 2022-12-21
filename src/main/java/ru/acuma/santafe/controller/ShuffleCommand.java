package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.impl.ShuffleService;

import java.util.List;

@Slf4j
@Component
public class ShuffleCommand extends BaseBotCommand {

    private final static List<Long> authorizedMembers = List.of(358831551L, 285250417L);

    private ShuffleService shuffleService;

    public ShuffleCommand() {
        super(PrivateCommand.SHUFFLE.getCommand(), "Shuffle members");
    }

    @Autowired
    public void setWishService(@Lazy ShuffleService shuffleService) {
        this.shuffleService = shuffleService;
    }

    @Override
    public void execute(Message message) {
        var userId = message.getFrom().getId();
        if (authorizedMembers.contains(userId)) {
            shuffleService.shuffle();
        }
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
