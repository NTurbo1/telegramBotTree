package com.nturbo1.telegramBot.telebot.commands;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implements the help command
 */
@Component
@AllArgsConstructor
public class HelpCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;

    /**
     * Responds with all the commands and their descriptions
     * @param update
     */
    @Override
    public void execute(Update update) {

        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText(categoryService.help());

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            //TODO: Handle exception
            e.printStackTrace();
        }
    }
}
