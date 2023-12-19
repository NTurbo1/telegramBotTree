package com.nturbo1.telegramBot.telebot.commands.interfaces;

import com.nturbo1.telegramBot.telebot.Telebot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    public abstract void execute(Update update);
}
