package ru.acuma.santafe.service.api;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public interface IExecuteService {

    <T extends Serializable, M extends BotApiMethod<T>> T execute(M method);

    <T extends Serializable, M extends BotApiMethod<T>> T executeApi(M method);
}
