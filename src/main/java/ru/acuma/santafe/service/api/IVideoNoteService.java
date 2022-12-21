package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IVideoNoteService {
    void applyNote(Update update);
}
