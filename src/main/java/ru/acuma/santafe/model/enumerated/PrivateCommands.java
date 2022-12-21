package ru.acuma.santafe.model.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivateCommands {

    START("start"),
    WISH("wish");

    private final String command;

}
