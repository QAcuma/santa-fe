package ru.acuma.santafe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.acuma.santafe.model.record.Wish;

import java.util.List;

@Repository
public interface WishRepository extends MongoRepository<Wish, Long> {

    List<Wish> findAllByTelegramIdFromAndYearEquals(Long telegramId, Integer year);

}
