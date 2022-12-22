package ru.acuma.santafe.model.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivateCommand {

    START("start"),
    SHUFFLE("shuffle"),
    FLUSH("flush"),
    CHOSEN("chosen"),
    MEMBERS("members"),
    REMIND("remind");

    private final String command;

}
