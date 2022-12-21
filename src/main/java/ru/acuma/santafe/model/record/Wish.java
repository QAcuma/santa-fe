package ru.acuma.santafe.model.record;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.telegram.telegrambots.meta.api.objects.VideoNote;

public record Wish(
        @Id
        ObjectId id,
        Long telegramIdFrom,
        String telegramChatFrom,
        Integer telegramMessageId,
        VideoNote video,
        Integer year
) {
}
