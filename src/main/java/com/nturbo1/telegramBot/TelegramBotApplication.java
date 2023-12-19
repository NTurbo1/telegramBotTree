package com.nturbo1.telegramBot;

import com.nturbo1.telegramBot.telebot.Telebot;
import com.nturbo1.telegramBot.telebot.commands.ViewTreeCommand;
import com.nturbo1.telegramBot.telebot.commands.interfaces.Command;
import com.nturbo1.telegramBot.telebot.util.CommandNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ComponentScan("com.nturbo1.telegramBot")
public class TelegramBotApplication implements CommandLineRunner {

	@Autowired
	private Telebot telebot;
	@Autowired
	@Qualifier("viewTreeCommand")
	private Command viewTreeCommand;
	@Autowired
	@Qualifier("addElementCommand")
	private Command addElementCommand;
	@Autowired
	@Qualifier("removeElementCommand")
	private Command removeElementCommand;
	@Autowired
	@Qualifier("helpCommand")
	private Command helpCommand;
	@Autowired
	@Qualifier("downloadCommand")
	private Command downloadCommand;
	@Autowired
	@Qualifier("uploadCommand")
	private Command uploadCommand;


	public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class, args);
	}

	@Override
	public void run(String... args) {

		addCommands();

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(telebot);
		} catch (TelegramApiException e) {
			// TODO: Handle later
			e.printStackTrace();
		}
	}

	private void addCommands() {
		telebot.addCommand(CommandNames.VIEW_TREE_COMMAND, viewTreeCommand);
		telebot.addCommand(CommandNames.ADD_ELEMENT_COMMAND, addElementCommand);
		telebot.addCommand(CommandNames.REMOVE_ELEMENT_COMMAND, removeElementCommand);
		telebot.addCommand(CommandNames.HELP_COMMAND, helpCommand);

		telebot.addCommand(CommandNames.DOWNLOAD_COMMAND, downloadCommand);
		telebot.addCommand(CommandNames.UPLOAD_COMMAND, uploadCommand);
	}

}

