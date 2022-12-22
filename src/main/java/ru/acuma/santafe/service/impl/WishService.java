package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import ru.acuma.santafe.model.domain.Wish;
import ru.acuma.santafe.model.enumerated.GiftStatus;
import ru.acuma.santafe.repository.WishRepository;
import ru.acuma.santafe.service.api.IExecuteService;
import ru.acuma.santafe.service.api.IMessageService;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IWishService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService implements IWishService {

    private final WishRepository wishRepository;
    private final IMessageService messageService;
    private final IExecuteService executeService;
    private final ISantaService santaService;

    @Override
    public void accept(VideoNote video, User from, String chatId, Integer messageId) {
        var wish = Wish.builder()
                .id(ObjectId.get())
                .telegramIdFrom(from.getId())
                .telegramChatFrom(chatId)
                .telegramMessageId(messageId)
                .video(video)
                .year(LocalDateTime.now().getYear())
                .status(GiftStatus.WISHED)
                .build();

        var existedWishes = findYearWishes(from.getId());
        var text = messageService.acceptedWishMessage(from.getFirstName());

        if (!existedWishes.contains(wish)) {
            wishRepository.save(wish);
        } else {
            text = messageService.wishAlreadyExists(from.getFirstName());
        }

        var message = new SendMessage(chatId, text);

        executeService.execute(message);
    }

    @Override
    public void remindUserWishes(Long telegramId, Long chatId) {
        var wishes = findYearWishes(telegramId);
        var stringChatId = String.valueOf(chatId);
        var santa = santaService.findByTelegramId(telegramId);

        if (santa == null) {
            var text = """
                    Ты ещё не записал своё желание :(
                                        
                    Поспеши оставить кружок с хотелкой в чате сант!
                    """;
            var response = new SendMessage(String.valueOf(chatId), text);
            executeService.execute(response);

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
                wish.getTelegramChatFrom(),
                wish.getTelegramMessageId());

        executeService.executeApi(video);
    }

    private List<Wish> findYearWishes(Long telegramId) {
        var year = LocalDateTime.now().getYear();

        return wishRepository.findAllByTelegramIdFromAndYearEqualsAndStatusIn(
                telegramId,
                year,
                List.of(GiftStatus.WISHED, GiftStatus.ASSIGNED)
        );
    }

    @Override
    public List<Long> findWishHolders() {
        return wishRepository.findAll().stream()
                .map(Wish::getTelegramIdFrom)
                .distinct()
                .toList();
    }
}
