package ru.acuma.santafe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IWishService;

@Slf4j
@Component
public class MembersCommand extends BaseBotCommand {

    private final IWishService wishService;
    private final ISantaService santaService;
    private final IExecuteService executeService;

    public MembersCommand(@Lazy IWishService wishService, @Lazy ISantaService santaService, @Lazy IExecuteService executeService) {
        super(PrivateCommand.MEMBERS.getCommand(), "Display active santas");
        this.wishService = wishService;
        this.santaService = santaService;
        this.executeService = executeService;
    }

    @Override
    public void execute(Message message) {
        var wishHolders = wishService.findWishHolders();
        var santas = santaService.findAllActiveSantas(wishHolders);
        var builder = new StringBuilder();
        builder.append(santas.isEmpty() ? "Пока что санты не приняли участие" : "Список учавствующих сант:")
                .append(System.lineSeparator())
                .append(System.lineSeparator());
        santas.forEach(santa -> {
            builder.append(santa.toString());
            builder.append("   ");
            builder.append(santa.getKnowVictim() ? "✅" : "❓");
            builder.append(System.lineSeparator());
        });
        var response = new SendMessage(
                String.valueOf(message.getChatId()),
                builder.toString()
        );
        executeService.execute(response);
    }

    @Override
    protected Boolean denyInGroups() {
        return Boolean.FALSE;
    }

}
