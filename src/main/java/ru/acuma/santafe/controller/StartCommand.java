package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommands;

@Slf4j
@Component
public class StartCommand extends BaseBotCommand {

    public StartCommand() {
        super(PrivateCommands.START.getCommand(), "Launch bot");
    }

    @Override
    public void execute(Message message) {
        log.info(message.toString());
    }

}
