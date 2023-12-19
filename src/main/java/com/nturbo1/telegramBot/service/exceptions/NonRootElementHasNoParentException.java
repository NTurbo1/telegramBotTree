package com.nturbo1.telegramBot.service.exceptions;

public class NonRootElementHasNoParentException extends Exception {

    public NonRootElementHasNoParentException(String message) {
        super(message);
    }
}
