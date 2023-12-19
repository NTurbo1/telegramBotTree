package com.nturbo1.telegramBot.telebot;

import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import com.nturbo1.telegramBot.telebot.util.CommandNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the telegram bot functionality
 */
@Component
public class Telebot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String apiKey;
    @Value("${telegram.bot.username}")
    private String username;

    private final Map<String, Command> commands;

    public Telebot() {
        this.commands = new HashMap<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && (update.getMessage().hasText() || update.getMessage().hasDocument())) {
            String message = update.getMessage().getCaption();

            if (message == null || message.isEmpty()) { // if there is no caption, get the text of the message
                message = update.getMessage().getText();
            }

            if (message == null || message.isEmpty()) {
                sendTextMessage(update.getMessage().getChatId(),
                        "No command is specified. Please send only these commands:");
                commands.get(CommandNames.HELP_COMMAND).execute(update);
            }
            if (message.startsWith("/")) {
                String[] words = message.split(" ");
                String commandName = words[0].substring(1);

                Command command = commands.get(commandName);
                if (command != null) {
                    command.execute(update);
                } else {
                    // Handle unknown command
                    sendTextMessage(update.getMessage().getChatId(), "Unknown command: " + commandName);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.apiKey;
    }

    public void addCommand(String commandName, Command command) {
        this.commands.put(commandName, command);
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
