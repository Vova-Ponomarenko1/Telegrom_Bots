package com.example.telegrom_bots;



import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

//C:/Program Files (x86)/Google/Chrome/Application/chrome.exe
public class Main {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println("Usage: Main <url>");
                System.exit(1);
            }

            String url = args[0];
            System.out.println(url);
            openChrome("https://www.forhub.io/soundcloud/");
            if ("https://on.soundcloud.com" == url) {
                return;
            } else if (url.isEmpty()) {
                closeChromeIncognito();
                return;
            }
            Thread.sleep(5000);
            typeURL(url);
            pressEnter();
            Thread.sleep(5000);
            //findButtonCoordinates();
            clickButton();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getLatestFile(String directory, String fileExtension) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(fileExtension));

        if (files == null || files.length == 0) {
            return null;
        }

        File latestFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (files[i].lastModified() > latestFile.lastModified()) {
                latestFile = files[i];
            }
        }

        return latestFile;
    }

    public static void closeChromeIncognito() throws IOException {
        String[] command = {"taskkill", "/F", "/IM", "chrome.exe"};
        Process process = Runtime.getRuntime().exec(command);
    }
    public static void openChrome(String url) throws IOException {
        String[] command = {"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe", "--incognito", url};
        Process process = Runtime.getRuntime().exec(command);
    }


    public static void typeURL(String url) throws AWTException {
        Robot robot = new Robot();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(url);
        clipboard.setContents(stringSelection, null);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public static void pressEnter() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void clickButton() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        int x = 300; // X-coordinate of the Download The Song button (coordinates may vary)
        int y = 655; // Y-coordinate of the Download The Song button (coordinates may vary)
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(4000);
        pressEnter();
    }
}


