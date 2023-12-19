package com.nturbo1.telegramBot.telebot.commands;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implements the /download command.
 *
 * If the operation is successful, it sends an Excel file with a caption
 *      "Excel tree file".
 *
 * If there is an Exception during the process, it sends a caption:
 *      "An error occurred :(.".
 */
@Component
@AllArgsConstructor
public class DownloadCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;
    @Override
    public void execute(Update update) {

        SendDocument response = new SendDocument();
        response.setChatId(update.getMessage().getChatId().toString());

        try {
            InputFile excelFile = new InputFile(categoryService.downloadTreeAsExcel());
            response.setCaption("Excel tree file");
            response.setDocument(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCaption("An error occurred :(.");
        }

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            //TODO: use a logger
            e.printStackTrace();
        }
    }
}
