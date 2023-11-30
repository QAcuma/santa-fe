package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.IShuffleService;

import java.util.List;

@Slf4j
@Component
public class ShuffleCommand extends BaseBotCommand {

    private final static List<Long> authorizedMembers = List.of(358831551L, 285250417L);

    private final IShuffleService shuffleService;
    private final IExecuteService executeService;

    public ShuffleCommand(@Lazy IShuffleService shuffleService, @Lazy IExecuteService executeService) {
        super(PrivateCommand.SHUFFLE.getCommand(), "Shuffle members");
        this.shuffleService = shuffleService;
        this.executeService = executeService;
    }

    @Override
    public void execute(Message message) {
        var userId = message.getFrom().getId();
        var delete = new DeleteMessage(
                String.valueOf(message.getChatId()),
                message.getMessageId());
        executeService.execute(delete);
        if (!authorizedMembers.contains(userId)) {
            return;
        }

        shuffleService.shuffle(String.valueOf(message.getChatId()));
        var response = new SendMessage(
                String.valueOf(message.getChatId()),
                """
                     🎅 Санты назначены! 🎅
                     Скорее перейди по ссылке и узнай своего счастливчика
                     
                     https://t.me/santa_fetch_bot?start=santa
                     """);
        executeService.execute(response);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.FALSE;
    }
}
