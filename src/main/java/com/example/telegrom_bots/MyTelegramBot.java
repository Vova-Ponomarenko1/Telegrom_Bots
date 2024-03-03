package com.example.telegrom_bots;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.telegrom_bots.Main.getLatestFile;


@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.username}")
    private String botName;

    public MyTelegramBot() {
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }
    private boolean USER_DOWNLOADS;
    private int SUPER_ADMIN_ID = 00000;
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();
            System.out.println(userId);
            if (userId == SUPER_ADMIN_ID) {
                handleAdminCommands(messageText, chatId, update);
            } else {
                handleUserCommands(messageText, chatId, update);
            }
        }
    }

    private void handleUserCommands(String messageText, long chatId,Update update) {
        switch (messageText) {
            case "/start":
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                break;
            case "Привіт":
                helloMessage(chatId);
                break;
            case "/help": sayUserComand(chatId);
            default:
                System.out.println("end");
        }
        if (messageText.startsWith("/t") && USER_DOWNLOADS) {
            String url = parseMessageUrl(messageText);
            Main.main(new String[]{url});
            try {
                if (!url.isEmpty()){
                    trackDownload(chatId, update.getMessage().getChat().getFirstName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleAdminCommands(String messageText, long chatId, Update update) {
        switch (messageText) {
            case "/start":
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                return;
            case "Привіт":
                helloMessage(chatId);
                break;
            case "/admin":
                sayHiFather(chatId, update.getMessage().getChat().getFirstName());
                break;
            case "/help+": sayAdminComand(chatId);
                break;
            case "/t+": userDownloads(chatId);
                break;
            case "/Super_stop1": System.exit(1);
                break;
            default:
                handleUserCommands(messageText, chatId, update);
        }

        if (messageText.startsWith("/s") && update.getMessage().getFrom().getId() == SUPER_ADMIN_ID) {
            String url = parseMessageUrl(messageText);
            Main.main(new String[]{url});
            try {
                if (!url.isEmpty()){
                    trackDownload(chatId, update.getMessage().getChat().getFirstName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name + "";
        sendMessage(chatId, answer);
    }

    private void helloMessage(long chatId) {
        String answer = "Привіт";
        sendMessage(chatId, answer);
    }
    private void userDownloads(long chatId) {
        if (USER_DOWNLOADS) {
            USER_DOWNLOADS = false;
        } else  {
            USER_DOWNLOADS = true;
        }
        String answer = "Status = " + USER_DOWNLOADS;
        sendMessage(chatId, answer);
    }
    private void sayHiFather(long chatId, String name) {
        String answer = "Здаров отец";
        sendMessage(chatId, answer);
    }
    private void sayUserComand(long chatId) {
        String answer = "1./start \n 2.Привіт\n 3./help";
        sendMessage(chatId, answer);
    }

    private void sayAdminComand(long chatId) {
        String answer = "1./start \n 2.Привіт\n 3./admin_1\n 4./admin\n 5./help+ \n 6./s (link to soundcloud)\n 7./Super_stop \n 8./s (Don't link to closet browser)";
        sendMessage(chatId, answer);
    }
    private void trackDownload(long chatId, String name) throws InterruptedException {
        Thread.sleep(2000);
        String downloadDirectory = "D:/Downloads";
        String fileExtension = ".mp3";
        File latestFile = getLatestFile(downloadDirectory, fileExtension);
        sendFile(chatId, latestFile);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendFile(long chatId, File fileToSend) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        InputFile inputFile = new InputFile(fileToSend);
        sendDocument.setDocument(inputFile);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public String parseMessageUrl(String message) {
        String regex = "https?://\\S+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        String link = "";
        if (matcher.find()) {
            link = matcher.group(0);
        }

        return link;
    }
}

