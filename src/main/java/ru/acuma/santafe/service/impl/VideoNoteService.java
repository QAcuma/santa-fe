package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.acuma.santafe.service.api.IGiftService;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IVideoNoteService;

@Service
@RequiredArgsConstructor
public class VideoNoteService implements IVideoNoteService {

    private final IGiftService giftService;
    private final ISantaService santaService;

    @Override
    public void applyNote(Update update) {
        var from = getFrom(update);
        var video = update.getMessage().getVideoNote();
        if (!santaService.existById(from.getId())) {
            santaService.checkInSanta(from);
        }

        giftService.accept(
                video,
                from,
                String.valueOf(update.getMessage().getChatId()),
                update.getMessage().getMessageId()
        );
    }

    private User getFrom(Update update) {
        if (update.getMessage().getForwardFrom() != null) {
            return update.getMessage().getForwardFrom();
        }
        return update.getMessage().getFrom();

    }
}
