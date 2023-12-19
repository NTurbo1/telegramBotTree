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
 * Implements the viewTree command
 */
@Component
@AllArgsConstructor
public class ViewTreeCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;

    /**
     * Responds with a tree structure of the current categories.
     * @param update
     */
    @Override
    public void execute(Update update) {
        String tree = categoryService.printTree();

        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText("Tree view:\n" + tree);

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            //TODO: use a logger
            e.printStackTrace();
        }
    }
}
