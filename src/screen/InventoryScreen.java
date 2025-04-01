package screen;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class InventoryScreen implements Screen{
    GamePanel gp;
    Graphics2D g2;

    BufferedImage statPanel;

    public int commandNum = 0;
    public int charNum = 0;

    public InventoryScreen (GamePanel gp){
        this.gp = gp;
    }

    public void loadImages() {
        try {
            statPanel = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/statPanel.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyImages(){
        System.out.println("Title Images Unloaded");
        statPanel = null;
    }

    @Override
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(charNum == 0){
            drawStats(gp.player);
            gp.player.calculateStats();
        }
        else if (charNum == 1){
            drawStats(gp.companion1);
        }
        else{
            drawStats(gp.companion2);
        }
    }

    public void drawStats(Entity entity){
        //Inventory Frame
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*15+gp.tileSize/2;
        int frameHeight  = gp.tileSize*12 + gp.tileSize/2;
        g2.drawImage(statPanel, frameX, frameY, frameWidth, frameHeight, null);


        //Inventory Content
        g2.setColor(new Color(59, 61, 62));
        int x = gp.tileSize*3+gp.tileSize/2;
        int y = gp.tileSize*4-7;

        g2.drawImage(entity.down1, x, y, gp.tileSize*4, gp.tileSize*4, null);

        x = gp.tileSize*3+8;
        y = gp.tileSize*2+17;
        g2.setFont(g2.getFont().deriveFont(25f));
        g2.drawString(entity.getName(), x, y);
        g2.drawString("Q", x - gp.tileSize - gp.tileSize/2 + 15, y);
        g2.drawString("R", x + gp.tileSize*4 + gp.tileSize/2-2, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
        x = gp.screenWidth/2 - gp.tileSize;
        y = gp.tileSize*4;
        g2.drawString("LEVEL: " + gp.df.format(entity.level), x, y);
        if(entity == gp.player){
            g2.drawString("STAT POINTS: " + gp.player.statPoints, x + gp.tileSize*3, y);
        }
        y += gp.tileSize/2;
        g2.drawString("EXP: " + gp.df.format(entity.exp), x, y);
        y += gp.tileSize/2;
        g2.drawString("EXP TO NEXT LEVEL: " + gp.df.format((entity.nextLevelExp-entity.exp)), x, y);

        y = gp.tileSize*6-8;
        g2.drawString("HEALTH: " + gp.df.format(entity.hp) + "/" + gp.df.format(entity.maxHP), x, y);
        y += gp.tileSize/2;
        g2.drawString("ENERGY: " + gp.df.format(entity.energy) + "/" + gp.df.format(entity.maxEnergy), x, y);

        y = gp.tileSize*7-8;
        g2.drawString("ATTACK: " + gp.df.format(entity.attack), x, y);
        y += gp.tileSize/2;
        g2.drawString("DEFENSE: " + gp.df.format(entity.defense), x, y);
        y += gp.tileSize/2;
        g2.drawString("SPEED: " + gp.df.format(entity.speed), x, y);

        y = gp.tileSize*9-8;
        g2.drawString("VITALITY: " + gp.df.format(entity.vit), x, y);
        y += gp.tileSize/2;
        g2.drawString("POWER: " + gp.df.format(entity.pow), x, y);
        y += gp.tileSize/2;
        g2.drawString("MAGIC: " + gp.df.format(entity.mag), x, y);
        y += gp.tileSize/2;
        g2.drawString("AGILITY: " + gp.df.format(entity.agi), x, y);
        y += gp.tileSize/2;
        g2.drawString("LUCK: " + gp.df.format(entity.luck), x, y);
        if(entity == gp.player && gp.player.statPoints > 0){
            addStatPoints(g2, x, gp.tileSize*9-8);
        }
    }

    public void addStatPoints(Graphics2D g2, int x, int y){

        g2.setColor(new Color(255,255,255));
        g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
        g2.setColor(new Color(0,0,0, 150));
        g2.drawString("+", x+gp.tileSize*3, y);
        g2.setColor(new Color(255,255,255));
        if(commandNum == 0){
            g2.setColor(new Color(255, 0, 0, 255));
            g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.setColor(new Color(0,0,0, 150));
            g2.drawString("+", x+gp.tileSize*3, y);
            g2.setColor(new Color(255,255,255));
        }
        y += gp.tileSize/2;
        g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
        g2.setColor(new Color(0,0,0, 150));
        g2.drawString("+", x+gp.tileSize*3, y);
        g2.setColor(new Color(255,255,255));
        if(commandNum == 1){
            g2.setColor(new Color(255, 0, 0, 255));
            g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.setColor(new Color(0,0,0, 150));
            g2.drawString("+", x+gp.tileSize*3, y);
            g2.setColor(new Color(255,255,255));
        }
        y += gp.tileSize/2;
        g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
        g2.setColor(new Color(0,0,0, 150));
        g2.drawString("+", x+gp.tileSize*3, y);
        g2.setColor(new Color(255,255,255));
        if(commandNum == 2){
            g2.setColor(new Color(255, 0, 0, 255));
            g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.setColor(new Color(0,0,0, 150));
            g2.drawString("+", x+gp.tileSize*3, y);
            g2.setColor(new Color(255,255,255));
        }
        y += gp.tileSize/2;
        g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
        g2.setColor(new Color(0,0,0, 150));
        g2.drawString("+", x+gp.tileSize*3, y);
        g2.setColor(new Color(255,255,255));
        if(commandNum == 3){
            g2.setColor(new Color(255, 0, 0, 255));
            g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.setColor(new Color(0,0,0, 150));
            g2.drawString("+", x+gp.tileSize*3, y);
            g2.setColor(new Color(255,255,255));
        }
        y += gp.tileSize/2;
        g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
        g2.setColor(new Color(0,0,0, 150));
        g2.drawString("+", x+gp.tileSize*3, y);
        g2.setColor(new Color(255,255,255));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
        if(commandNum == 4){
            g2.setColor(new Color(255, 0, 0, 255));
            g2.drawOval(x+gp.tileSize*3, y-10, 8, 8);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.setColor(new Color(0,0,0, 150));
            g2.drawString("+", x+gp.tileSize*3, y);
            g2.setColor(new Color(255,255,255));
        }
    }
}
