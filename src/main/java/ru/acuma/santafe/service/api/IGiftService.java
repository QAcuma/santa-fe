package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.VideoNote;

public interface IGiftService {
    void accept(VideoNote video, User user, String chatId, Integer messageId);
}
