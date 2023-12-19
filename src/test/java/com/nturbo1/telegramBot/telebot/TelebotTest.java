package com.nturbo1.telegramBot.telebot;

import com.nturbo1.telegramBot.service.CategoryService;
import com.nturbo1.telegramBot.telebot.commands.ViewTreeCommand;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

class TelebotTest {

    @Test
    public void testViewTreeCommand() {
        // Mock necessary dependencies
        Telebot telebot = Mockito.spy(new Telebot());
        CategoryService categoryService = Mockito.mock(CategoryService.class);

        // Create a test update for the /viewTree command
        Update update = createTestUpdate("/viewTree");

        // Create the command instance
        Command viewTreeCommand = new ViewTreeCommand(telebot, categoryService);

        // Execute the command
        viewTreeCommand.execute(update);

        // Add assertions or verifications based on the expected behavior
        try {
            Mockito.verify(telebot).execute(Mockito.any(SendMessage.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Update createTestUpdate(String command) {
        User user = new User();
        user.setId(1L);
        user.setFirstName("TestUser");

        Chat chat = new Chat();
        chat.setId(123L);

        Message message = new Message();
        message.setChat(chat);
        message.setText(command);
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}