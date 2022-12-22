package ru.acuma.santafe.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.acuma.santafe.model.enumerated.PrivateCommand;
import ru.acuma.santafe.service.api.IKeyboardService;

import java.util.List;

@Service
public class KeyboardService implements IKeyboardService {

    @Override
    public InlineKeyboardMarkup startPrivateKeyboard() {
        var keyboard = new InlineKeyboardMarkup();
        var buttonChosen = new InlineKeyboardButton("Узнать что дарить");
        buttonChosen.setCallbackData(PrivateCommand.CHOSEN.getCommand());
        var buttonRemind = new InlineKeyboardButton("Моё пожелание");
        buttonRemind.setCallbackData(PrivateCommand.REMIND.getCommand());
        var row = List.of(buttonChosen, buttonRemind);
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

}
