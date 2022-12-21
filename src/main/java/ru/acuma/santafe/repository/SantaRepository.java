package ru.acuma.santafe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.acuma.santafe.model.domain.Santa;

@Repository
public interface SantaRepository extends MongoRepository<Santa, Long> {

    Santa findByTelegramId(Long telegramId);

}
