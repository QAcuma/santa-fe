package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import ru.acuma.santafe.model.record.Santa;
import ru.acuma.santafe.model.record.Wish;
import ru.acuma.santafe.repository.WishRepository;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.IGiftService;
import ru.acuma.santafe.service.api.IMessageService;
import ru.acuma.santafe.service.api.ISantaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService implements IGiftService {

    private final WishRepository wishRepository;
    private final IMessageService messageService;
    private final IExecuteService executeService;
    private final ISantaService santaService;

    @Override
    public void accept(VideoNote video, User from, String chatId, Integer messageId) {
        var wish = new Wish(
                ObjectId.get(),
                from.getId(),
                chatId,
                messageId,
                video,
                LocalDateTime.now().getYear()
        );

        if (findYearWishes(from.getId()).isEmpty()) {
            wishRepository.save(wish);
        }

        var text = messageService.acceptedWishMessage(from.getFirstName());
        var message = new SendMessage(chatId, text);

        executeService.execute(message);
    }

    public void remindUserWishes(Long telegramId, Long chatId) {
        var wishes = findYearWishes(telegramId);
        var stringChatId = String.valueOf(chatId);
        var santa = santaService.findByTelegramId(telegramId);
        if (santa == null) {
            return;
        }

        if (!wishes.isEmpty()) {
            var text = messageService.remindWishMessage(santa.toString());
            var message = new SendMessage(stringChatId, text);
            executeService.execute(message);
        } else {
            var text = messageService.missingWishMessage(santa.toString());
            var message = new SendMessage(stringChatId, text);
            executeService.execute(message);
        }

        wishes.forEach(wish -> remind(wish, String.valueOf(chatId)));
    }

    private void remind(Wish wish, String chatId) {
        var video = new ForwardMessage(
                chatId,
                wish.telegramChatFrom(),
                wish.telegramMessageId());

        executeService.executeApi(video);

    }

    private List<Wish> findYearWishes(Long telegramId) {
        var year = LocalDateTime.now().getYear();

        return wishRepository.findAllByTelegramIdFromAndYearEquals(telegramId, year);
    }

}
