package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.acuma.santafe.model.domain.Santa;
import ru.acuma.santafe.repository.SantaRepository;
import ru.acuma.santafe.service.api.ISantaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SantaService implements ISantaService {

    private final SantaRepository santaRepository;

    @Override
    public void checkInSanta(User user) {
        if (santaRepository.existsById(user.getId())) {
            return;
        }
        var santa = Santa.builder()
                .telegramId(user.getId())
                .login(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .knowVictim(Boolean.FALSE)
                .build();

        santaRepository.save(santa);
    }

    @Override
    public Santa findByTelegramId(Long telegramId) {
        return santaRepository.findByTelegramId(telegramId);
    }

    @Override
    public List<Santa> findAllActiveSantas(List<Long> santaIds) {
        return santaRepository.findAllById(santaIds);
    }

    @Override
    public void saveAll(List<Santa> santas) {
        santaRepository.saveAll(santas);
    }

    @Override
    public void save(Santa santa) {
        santaRepository.save(santa);
    }

}
