package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IChosenService;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.IKeyboardService;
import ru.acuma.santafe.service.api.IMessageService;

@Slf4j
@Component
public class StartCommand extends BaseBotCommand {

    private final IMessageService messageService;
    private final IChosenService chosenService;
    private final IKeyboardService keyboardService;
    private final IExecuteService executeService;

    public StartCommand(@Lazy IMessageService messageService, @Lazy IChosenService chosenService, @Lazy IKeyboardService keyboardService, @Lazy IExecuteService executeService) {
        super(PrivateCommand.START.getCommand(), "Launch bot");
        this.messageService = messageService;
        this.chosenService = chosenService;
        this.keyboardService = keyboardService;
        this.executeService = executeService;
    }

    @Override
    public void execute(Message message) {
        var victim = chosenService.lookup(message.getFrom().getId(), message.getChatId());
        var victimName = victim == null
                ? ""
                : victim.toString();
        var text = messageService.startPrivateChatMessage(victimName);
        var keyboard = keyboardService.startPrivateKeyboard();
        var response = new SendMessage(String.valueOf(message.getChatId()), text);
        response.setReplyMarkup(keyboard);

        executeService.executeApi(response);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
