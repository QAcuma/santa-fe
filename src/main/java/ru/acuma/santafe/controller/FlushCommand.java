package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IWishService;

import java.util.List;

@Slf4j
@Component
public class FlushCommand extends BaseBotCommand {

    private static final List<Long> authorizedMembers = List.of(358831551L, 285250417L);

    private final IExecuteService executeService;
    private final IWishService wishService;
    private final ISantaService santaService;

    public FlushCommand(@Lazy IExecuteService executeService, @Lazy IWishService wishService, @Lazy ISantaService santaService) {
        super(PrivateCommand.FLUSH.getCommand(), "FlushChatMessages");
        this.executeService = executeService;
        this.wishService = wishService;
        this.santaService = santaService;
    }

    @Override
    public void execute(Message message) {
        if (!authorizedMembers.contains(message.getFrom().getId())) {
            var delete = new DeleteMessage(
                    String.valueOf(message.getChatId()),
                    message.getMessageId());
            executeService.execute(delete);

            return;
        }

        wishService.flushWishes();
        santaService.flushSantas();
        var response = new SendMessage(
                String.valueOf(message.getChatId()),
                "Никто не забыт. Кроме всех сант и их желаний :с");
        executeService.execute(response);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.TRUE;
    }

}
