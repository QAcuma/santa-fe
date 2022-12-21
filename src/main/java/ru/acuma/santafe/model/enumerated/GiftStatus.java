package ru.acuma.santafe.model.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GiftStatus {

    WISHED,
    ASSIGNED,
    RECEIVED;

}
