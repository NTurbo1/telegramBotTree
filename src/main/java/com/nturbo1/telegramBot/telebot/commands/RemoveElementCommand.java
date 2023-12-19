package com.nturbo1.telegramBot.telebot.commands;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.service.exceptions.ElementNotFoundException;
import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implements the command:
 *      /removeElement <element name>
 *
 *      if the command arguments more than 1, it takes the first as the element name
 *      and ignores the rest of the arguments/words.
 *
 *      If there is no argument given, then it responds with a message:
 *          "Please, indicate the element's name.".
 *      If a Category with the element name not found, it responds with a message:
 *          "Element not found :(.".
 *      If the operation is successful, it responds with a message:
 *          "Element removed successfully".
 */
@Component
@AllArgsConstructor
public class RemoveElementCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;

    @Override
    public void execute(Update update) {

        String[] words = update.getMessage().getText().split(" ");
        String elementName = null;
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());

        if (words.length >= 2) {
            elementName = words[1];
            try {
                categoryService.removeElement(elementName);
            } catch (ElementNotFoundException e) {
                response.setText("Element not found :(.");
            }
        } else {
            response.setText("Please, indicate the element's name.");
        }

        response.setText("Element removed successfully");

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            //TODO: use a logger
            e.printStackTrace();
        }
    }
}

