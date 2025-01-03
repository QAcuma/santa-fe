package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.acuma.santafe.service.api.ISantaService;
import ru.acuma.santafe.service.api.IShuffleService;
import ru.acuma.santafe.service.api.IWishService;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShuffleService implements IShuffleService {

    private final IWishService wishService;
    private final ISantaService santaService;

    @Override
    public void shuffle(String chatId) {
        var santaIds = wishService.findWishHolders(chatId);
        var santas = santaService.findAllActiveSantas(santaIds);

        if (santas.size() < 2) {
            return;
        }

        Collections.shuffle(santas);
        log.info("Santas assigned in chat {}", chatId);

        var iterator = santas.iterator();
        var first = iterator.next();
        var current = first;
        while (iterator.hasNext()) {
            var nextSanta = iterator.next();
            current.setVictimTelegramId(nextSanta.getTelegramId());
            current = nextSanta;
        }
        current.setVictimTelegramId(first.getTelegramId());
        santaService.saveAll(santas);

        santas.forEach(santa -> log.info("login: {}, {} -> {}", santa.getLogin(), santa.getTelegramId(), santa.getVictimTelegramId()));
    }
}
