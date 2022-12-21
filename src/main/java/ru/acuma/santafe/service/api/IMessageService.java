package ru.acuma.santafe.service.api;

public interface IMessageService {
    String acceptedWishMessage(String userName);

    String remindWishMessage(String userName);

    String missingWishMessage(String valueOf);
}
