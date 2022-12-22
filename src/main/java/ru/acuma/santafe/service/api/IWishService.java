package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import ru.acuma.santafe.model.domain.Santa;

import java.util.List;

public interface IWishService {
    void accept(VideoNote video, User user, String chatId, Integer messageId);

    void sendWish(Santa santa, Long chatId);

    List<Long> findWishHolders();

    void flushWishes();
}
