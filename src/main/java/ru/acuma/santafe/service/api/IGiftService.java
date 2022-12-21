package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.VideoNote;

import java.util.List;

public interface IGiftService {
    void accept(VideoNote video, User user, String chatId, Integer messageId);

    void remindUserWishes(Long telegramId, Long chatId);

    List<Long> findWishHolders();
}
