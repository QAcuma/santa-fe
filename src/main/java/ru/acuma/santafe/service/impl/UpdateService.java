package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.acuma.santafe.service.api.IUpdateService;
import ru.acuma.santafe.service.api.IVideoNoteService;

@Service
@RequiredArgsConstructor
public class UpdateService implements IUpdateService {

    private final IVideoNoteService videoNoteService;

    @Override
    public void handle(Update update) {
        if (update.getMessage() != null && update.getMessage().hasVideoNote()) {
            videoNoteService.applyNote(update);
        }
    }


}
