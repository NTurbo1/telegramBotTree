package com.nturbo1.telegramBot.telebot.commands;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.service.exceptions.*;
import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Component
@AllArgsConstructor
public class UploadCommand implements Command {

    private final Telebot telebot;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(UploadCommand.class);

    @Override
    public void execute(Update update) {

        Document document = update.getMessage().getDocument();
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());

        if (document == null) {
            try {
                response.setText("Please, upload an Excel file.");
                telebot.execute(response);
            } catch (TelegramApiException e) {
                logger.error("Error sending response", e);
            }

            return;
        }

        File file = null;

        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(document.getFileId());
            String filePath = telebot.execute(getFile).getFilePath();
            file = telebot.downloadFile(filePath);

            logger.info("File ID: {}", document.getFileId());
            if (file != null) {
                logger.info("Downloaded file: {}", file.getAbsolutePath());
            }

            categoryService.uploadTreeFromExcel(file);

            response.setText("Uploaded the content successfully!");
        } catch (RootHasParentException e) {
            response.setText("There is a root element row with a parent id in your file. " +
                    "Root elements can't have parents. Please, fix it.\n" +
                    "All the rows before this row is saved. You don't need to upload them again ;).");
        } catch (ElementAlreadyExistsException e) {
            response.setText("There is an element row that already exists in your file. " +
                    "We can't have duplicate elements. Please, fix it.\n" +
                    "All the rows before this row is saved. You don't need to upload them again ;).");
        } catch (ParentNotFoundException | NonRootElementHasNoParentException e) {
            response.setText("There is a non root element row with a non-existing parent id in your file. " +
                    "Please, include existing parent ids only.\n" +
                    "All the rows before this row is saved. You don't need to upload them again ;).");
        } catch (InvalidDataType e) {
            response.setText("There is an element row with a field of invalid data type in your file. " +
                    "Please make sure the row fields are of correct data types.\n" +
                    "All the rows before this row is saved. You don't need to upload them again ;).");
        } catch(Exception e) {
            logger.error("An error occurred while processing the uploaded file", e);
            response.setText("An error occurred :(.");
        } finally {
            if (file != null) {
                file.delete();
            }
        }

        try {
            telebot.execute(response);
        } catch (TelegramApiException e) {
            logger.error("Error sending response", e);
        }
    }
}
