package ru.acuma.santafe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.acuma.santafe.service.api.IShuffleService;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShuffleService implements IShuffleService {

    private final WishService wishService;
    private final SantaService santaService;

    @Override
    public void shuffle() {
        var santaIds = wishService.findWishHolders();
        var santas = santaService.findAllActiveSantas(santaIds);

        if (santas.isEmpty()) {
            return;
        }

        Collections.shuffle(santas);

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

        santas.forEach(santa -> log.debug("login: {}, {} -> {}", santa.getLogin(), santa.getTelegramId(), santa.getVictimTelegramId()));
    }

}
