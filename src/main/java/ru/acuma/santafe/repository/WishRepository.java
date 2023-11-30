package ru.acuma.santafe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.acuma.santafe.model.domain.Wish;
import ru.acuma.santafe.model.enumerated.GiftStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishRepository extends MongoRepository<Wish, Long> {

    List<Wish> findAllByTelegramIdFromAndYearEqualsAndStatusIn(Long telegramId, Integer year, List<GiftStatus> statuses);

    List<Wish> findByChatId(String chatId);
}
