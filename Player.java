/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.*;
/**
 *
 * @author nass_mac
 */
public class Player {
    private int posX, posY;
    private int velX = 0, max_X, min_X;
    
    // advanced features
    private SpriteBase scene;
    private Image face;
    private int gunPointX, gunPointY;
    private int health = Constants.PLAYER_MAX_HEALTH;
    private int score = 0;
    private float scoreMultiplier = 5;
    
    public Player(int x, int y) {
        posX = x;
        posY = y;
        
        initPlayer();
    }
    
    private void initPlayer() {
        max_X = Constants.SCREEN_WIDTH - (2 * Constants.PLAYER_WIDTH);
        min_X = Constants.PLAYER_WIDTH + 2;
        gunPointY = posY;
        updateGunPoint();
        
        // setup sprite
        scene = new SpriteBase
        ("space.png", (int) posX, (int) posY, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        face = scene.getFace();
    }
    
    public void updateGunPoint() {
        gunPointX = posX + (Constants.PLAYER_WIDTH / 2) - 1;
    }
    
    public Rectangle bounds() {
        return new Rectangle(this.getPosX(), this.getPosY(), 
                Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }
    
    public void reduceHealth() {
        if(health > 0)
            health--;
    }
    
    public boolean isAlive() {
        if(health > 0)
            return true;
        else
            return false;
    }
    
    public void moveRight() {
        if(posX < max_X)
            posX += 18;
        updateGunPoint();
    }
    
    public void moveLeft() {
        if(posX > min_X)
            posX -= 18;
        updateGunPoint();
    }
    
    public void increaseScore() {
        score += (50 * scoreMultiplier);
    }
    
    public void decreaseScoreMult() {
        if(scoreMultiplier > 1)
            scoreMultiplier /= 1.0005;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Image getFace() {
        return face;
    }

    public int getGunPointX() {
        return gunPointX;
    }

    public int getGunPointY() {
        return gunPointY;
    }

    public int getHealth() {
        return health;
    }
    
    public int getScore() {
        return score;
    }
    
    public float getScoreMult() {
        return scoreMultiplier;
    }

}
