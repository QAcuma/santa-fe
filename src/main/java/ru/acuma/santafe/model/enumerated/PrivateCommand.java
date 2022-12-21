package ru.acuma.santafe.model.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivateCommand {

    START("start"),
    SHUFFLE("shuffle"),
    CHOSEN("chosen"),
    WISH("wish");

    private final String command;

}
