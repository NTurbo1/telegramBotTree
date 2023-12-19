package com.nturbo1.telegramBot.telebot.commands;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.service.dto.CategoryDto;
import com.nturbo1.telegramBot.service.exceptions.ElementAlreadyExistsException;
import com.nturbo1.telegramBot.service.exceptions.ParentNotFoundException;
import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implements the addElement command
 */
@Component
@AllArgsConstructor
public class AddElementCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;

    /**
     * Implements both versions:
     *      /addElement <element name>
     *      /addElement <parent name> <element name>
     *
     *      if the command arguments more than 2, it takes the first as the parent name, the
     *      second as the element name, and ignores the rest of the arguments/words.
     *
     *      If there is only 1 argument, it takes it as the element name.
     *
     *      If there is no argument given, then it responds with a message:
     *          "Please, indicate the element's name.".
     *      If the parent not found, it responds with a message:
     *          "Parent is not found :(.".
     *      If the operation is successful, it responds with a message:
     *          "Element added successfully".
     *
     *      If a Category with the element name already exists, it responds with the message:
     *          "Element already exists."
     *
     * @param update
     */
    @Override
    public void execute(Update update) {

        String[] words = update.getMessage().getText().split(" ");
        String parentName = null;
        String elementName = null;
        CategoryDto addedCategory = null;

        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());

        if (words.length >= 3) {
            parentName = words[1];
            elementName = words[2];

            try {
                addedCategory = categoryService.addElement(parentName, elementName);
                response.setText("Element added successfully");
            } catch (ParentNotFoundException e) {
                response.setText("Parent is not found :(.");
            } catch (ElementAlreadyExistsException e) {
                response.setText("Child element already exists.");
            }
        } else if (words.length == 2) {
            elementName = words[1];

            try {
                addedCategory = categoryService.addElement(elementName);
                response.setText("Element added successfully");
            } catch (ElementAlreadyExistsException e) {
                response.setText("Element already exists.");
            }
        } else {
            response.setText("Please, indicate the element's name.");
        }

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            //TODO: use a logger
            e.printStackTrace();
        }
    }
}

