package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface IKeyboardService {
    InlineKeyboardMarkup startPrivateKeyboard();
}
