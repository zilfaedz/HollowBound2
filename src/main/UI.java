package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Image;

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
    BufferedImage titleBackground;

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
//        Entity heart = new OBJ_Heart(gp);
//
//        heart_full = heart.image1;
//        heart_half = heart.image2;
//        heart_blank = heart.image3;
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
        else if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        else if(gp.gameState == gp.inventoryState){
            drawPlayerLife();
            drawInventoryScreen();
        }
        else if (gp.gameState == gp.newGameState) {
            drawNewGameScreen();
        }
        else if (gp.gameState == gp.loadGameState) {
            drawLoadGameScreen();
        }
        else if (gp.gameState == gp.settingsState) {
            drawSettingsScreen();
        }
        else if (gp.gameState == gp.characterPickState) {
            drawCharacterPickScreen();
        }
        else if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        else if(gp.gameState == gp.mapState){
            gp.map.drawFullMapScreen(g2);
        }
    }


    public void drawPlayerLife(){

        int x = 0;
        int y = 0;

        double oneScale = (gp.tileSize*5-3)/gp.player.maxLife;
        double hpBarValue = oneScale * gp.player.life;

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(x, y, gp.tileSize*5, 16);

        g2.setColor(new Color(255,0,30));
        g2.fillRect(x, y, (int) hpBarValue, 14);

        String text = Double.toString(gp.player.life);
        String text2 = Double.toString(gp.player.maxLife);
        g2.setFont(g2.getFont().deriveFont( 14f));
        g2.setColor(Color.black);
        g2.drawString(text+"/"+text2, x+5, y+12);

        y += gp.tileSize/3;

        double oneScale1 = (gp.tileSize*5-3)/gp.player.maxEnergy;
        double energyBarValue = oneScale1 * gp.player.energy;

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(x, y, gp.tileSize*5, 16);

        g2.setColor(new Color(255, 227, 24));
        g2.fillRect(x, y, (int) energyBarValue, 14);

        text = Double.toString(gp.player.energy);
        text2 = Double.toString(gp.player.maxEnergy);
        g2.setFont(g2.getFont().deriveFont( 14f));
        g2.setColor(Color.black);
        g2.drawString(text+"/"+text2, x+5, y+12);

        y = gp.screenHeight-18;

        double oneScale2 = (gp.tileSize*18)/gp.player.maxEnergy;
        double expBarValue = oneScale2 * gp.player.energy;

        g2.setColor(new Color(117, 117, 117));
        g2.fillRect(x, y, gp.screenWidth, 24);

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(x, y+3, (int) expBarValue, 14);

        text = gp.player.energy + "/" + gp.player.maxEnergy;
        x = getXforCenteredText(g2, text);
        g2.setFont(g2.getFont().deriveFont( 14f));
        g2.setColor(Color.black);
        g2.drawString(text, x, y+15);
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

        Entity background = new OBJ_Image(gp);
        titleBackground = background.image1;

        int x = 0;
        int y = 0;

        g2.drawImage(titleBackground, x, y, gp.screenWidth, gp.screenHeight, null);

        g2.setFont(g2.getFont().deriveFont(50f));
        String text = "Hollowbound";
        x = getXforCenteredText(g2, text);
        y = gp.screenHeight/5;

        g2.drawString(text, x, y);


        x = gp.screenWidth/2 - gp.tileSize;

        text = "NEW GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = getXforCenteredText(g2, text);
        y += gp.tileSize*6;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize+10, y);
        }


        g2.drawString(text, x-3, y-3);


        text = "LOAD GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = getXforCenteredText(g2, text);
        y += gp.tileSize+5;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize+10, y);
        }


        g2.drawString(text, x-3, y-3);

        text = "SETTINGS";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = getXforCenteredText(g2, text);
        y += gp.tileSize+5;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize+10, y);
        }


        g2.drawString(text, x-3, y-3);

        text = "QUIT GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = getXforCenteredText(g2, text);
        y += gp.tileSize+5;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize+10, y);
        }


        g2.drawString(text, x-3, y-3);
    }
    public void drawNewGameScreen(){

    }
    public void drawLoadGameScreen(){

    }
    public void drawSettingsScreen(){

    }
    public void drawCharacterPickScreen(){

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
            int imgX = gp.tileSize * 10;
            int imgY = gp.tileSize * 4;
            int imgHeight = gp.tileSize * 11;
            int imgWidth = gp.tileSize * 8;
            g2.drawImage(portrait, imgX, imgY, imgWidth, imgHeight, null);
        }

        x += gp.tileSize;
        y += gp.tileSize;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
        g2.setColor(Color.black);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawInventoryScreen(){
        //Inventory Frame
        int frameX = gp.tileSize;
        int frameY = gp.tileSize*3;
        int frameWidth = gp.tileSize*7;
        int frameHeight  = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        frameX = frameWidth + gp.tileSize*2;
        frameWidth += gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);



        //Inventory Content
        g2.setColor(Color.white);
        int x = gp.tileSize*2 + (gp.tileSize/2);
        int y = gp.tileSize*5;

        g2.drawImage(gp.player.down1, x, y, gp.tileSize*4, gp.tileSize*4, null);

//        x = gp.tileSize+10;
//        y = gp.tileSize*8+15;
//        g2.drawString("NAME: " + gp.player.getName(), x, y);
//        y += gp.tileSize/2;
//        g2.drawString("LEVEL: " + Double.toString(gp.player.level), x, y);
//
//        y += gp.tileSize;
//        g2.drawString("HEALTH: " + gp.player.life + "/" + gp.player.maxLife, x, y);
//        y += gp.tileSize/2;
//        g2.drawString("ENERGY: " + gp.player.energy + "/" + gp.player.maxEnergy, x, y);
//
//        y += gp.tileSize;
//        g2.drawString("ATTACK: ", x, y);
//        y += gp.tileSize/2;
//        g2.drawString("DEFENSE: ", x, y);
//        y += gp.tileSize/2;
//        g2.drawString("SPEED: " + gp.player.speed, x, y);
    }
//    public void drawForegroundFoliage(){
//        BufferedImage img = null;
//
//        try {
//            img = ImageIO.read(getClass().getResourceAsStream("/graphics/FoliageUR.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (img != null) {
//            int imgX = gp.tileSize * 12;
//            int imgY = 1;
//            int imgHeight = gp.tileSize * 8;
//            int imgWidth = gp.tileSize * 8;
//            g2.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);
//        }
//    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,20, 20);

        c = new Color (68, 255, 202);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 20, 20);
    }

    public int getXforCenteredText(Graphics2D g2, String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
