package ru.acuma.santafe.model.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupCommand {

    REGISTER("register");

    private final String command;

}
