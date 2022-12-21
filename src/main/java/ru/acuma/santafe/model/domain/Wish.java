package ru.acuma.santafe.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import ru.acuma.santafe.model.enumerated.GiftStatus;

@Getter
@Setter
@Builder
public class Wish {

    @Id
    private ObjectId id;
    private Long telegramIdFrom;
    private String chatId;
    private String telegramChatFrom;
    private Integer telegramMessageId;
    private Integer year;
    private VideoNote video;
    private GiftStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof Wish wish) {
            return new EqualsBuilder()
                    .append(telegramChatFrom, wish.telegramChatFrom)
                    .append(video.getFileUniqueId(), wish.video.getFileUniqueId()).isEquals();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(telegramChatFrom)
                .append(video.getFileUniqueId())
                .toHashCode();
    }
}
