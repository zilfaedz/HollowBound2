package main;

import entity.Entity;
import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage portrait;

    public boolean messageOn = false;
    ArrayList <String> message = new ArrayList<>();
    ArrayList <Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;

    public String currentDialogue = "";
    public int commandNum = 0;

    public boolean toggleTime = true;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp = gp;
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image1;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(gp.customFont);
        g2.setColor(Color.white);

        if (gameFinished && !messageOn) {
            gp.gameThread = null;
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            playTime += (double) 1 / 60;
//            drawMessage();
        }
        else if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.newGameState) {
            drawNewGameScreen();
        }
       if (gp.gameState == gp.loadGameState) {
            drawLoadGameScreen();
        }
        if (gp.gameState == gp.settingsState) {
            drawSettingsScreen();
        }
        if (gp.gameState == gp.characterPickState) {
            drawCharacterPickScreen();
        }

        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }


    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize/2;
        i = 0;

        while (i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize + 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22f));

        for (int i = 0; i < message.size(); i++){

            if (message.get(i) != null){

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen(){

        g2.setFont(g2.getFont().deriveFont(50f));
        String text = "Hollowbound";
        int x = getXforCenteredText(g2, text);
        int y = gp.screenHeight/5;

        g2.drawString(text, x, y);


        x = gp.screenWidth/2 - gp.tileSize;

        g2.drawImage(gp.player.portrait, x, y, gp.tileSize*13, gp.tileSize*15, null);

        text = "New Game";
        g2.setFont(g2.getFont().deriveFont(40f));
        x = gp.tileSize+5;
        y += gp.tileSize*6;
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize+10, y);
        }

        g2.drawString(text, x, y);

        text = "Load Game";
        g2.setFont(g2.getFont().deriveFont(40f));
        x += 10;
        y += gp.tileSize+5;
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize+10, y);
        }

        g2.drawString(text, x, y);

        text = "Settings";
        g2.setFont(g2.getFont().deriveFont(40f));
        x += 10;
        y += gp.tileSize+5;
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize+10, y);
        }

        g2.drawString(text, x, y);

        text = "Exit";
        g2.setFont(g2.getFont().deriveFont(40f));
        x += 10;
        y += gp.tileSize+5;
        if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize+10, y);
        }

        g2.drawString(text, x, y);
    }
    public void drawNewGameScreen(){

    }
    public void drawLoadGameScreen(){

    }
    public void drawSettingsScreen(){

    }
    public void drawCharacterPickScreen(){

    }
    public void drawPauseScreen(){
        Color c = new Color (0, 0, 0, 95);
        g2.setColor(c);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(175f));
        g2.setColor(Color.white);
        String text = "||";

        int x = getXforCenteredText(g2, text);
        int y = gp.screenHeight/2 + 40;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;
        int x = gp.tileSize;
        int y = gp.tileSize * 10;

        drawSubWindow(x, y, width, height);

        try {
            portrait = ImageIO.read(getClass().getResourceAsStream("/fort/portrait.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (portrait != null) {
            int imgX = gp.tileSize * 12;
            int imgY = gp.tileSize * 4;
            int imgHeight = gp.tileSize * 11;
            int imgWidth = gp.tileSize * 8;
            g2.drawImage(portrait, imgX, imgY, imgWidth, imgHeight, null);
        }

        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35, 35);

        c = new Color (255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXforCenteredText(Graphics2D g2, String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
