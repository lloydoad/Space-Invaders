/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author NASS
 */
public class Alien {
    // basic coordinate, movement and sizes
    private float posX, posY, offsetX, offsetY;
    private float vecX = 0, vecY = 0;
    private float max_X, max_Y;
    private int direction = 2;
    private float gunPointX, gunPointY;
    
    // advanced features
    private Image face;
    private Image faceDead;
    private SpriteBase scene;
    private SpriteBase sceneDead;
    private int state;
    
    // alien bullets
    private boolean canAttack = false;
    private Bullets shotsFired;
    
    public Alien(int posX, int posY) {
        // initial values
        this.posX = posX;
        this.posY = posY;

        initAlien();
    }

    public void updateAlien() {
        if(vecX <= 0) {
            direction = 1;
            vecY += 5;
        } else if(vecX >= Constants.ALIEN_WIDTH + 100) {
            direction = 2;
            vecY += 5;
        }
        
        if(direction == 1)
            vecX += 0.4;
        else if(direction == 2)
            vecX -= 0.4;
        
        // switch face if dead
        if(state == Constants.DEAD) {
            face = sceneDead.getFace();
        }
        
        // position on screen
        offsetX = posX + vecX;
        offsetY = posY + vecY;
        
        // get new bullet coordinates
        updateGunPoint();
        if(canAttack)
            fireDecision();
        
        // update coordinate of on screen bullets
        shotsFired.updateBullets();
    }
    
    private void updateGunPoint() {
        gunPointX = offsetX + (Constants.ALIEN_WIDTH / 2) - 1;
        gunPointY = offsetY + Constants.ALIEN_HEIGHT - 10;
    }
    
    private void initAlien() {
        // set max values
        max_X = posX + 100;
        max_Y = posY + 100;
        
        // update position
        offsetX = posX + vecX;
        offsetY = posY + vecY;
        
        // setup gunPoint
        updateGunPoint();
        shotsFired = new Bullets(Constants.DOWN);
        
        // setup sprite
        scene = new SpriteBase
        ("alien.png", (int) posX, (int) posY, Constants.ALIEN_WIDTH, Constants.ALIEN_HEIGHT);
        face = scene.getFace();
        
        // setup dead face
        sceneDead = new SpriteBase
        ("dead.png", (int) posX, (int) posY, Constants.ALIEN_WIDTH, Constants.ALIEN_HEIGHT);
    }
    
    private void fireDecision() {
        Random gen = new Random();
        int temp = gen.nextInt(610);

        if(temp <= 1)
            shotsFired.addBullet(new Bullet((int)this.getGunPointX(), (int)this.getGunPointY()));
    }
    
    public void setState(int input) {
        if(input == Constants.ALIVE)
            state = Constants.ALIVE;
        else if(input == Constants.DEAD)
            state = Constants.DEAD;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public int getState() {
        return state;
    }

    public Image getFace() {
        return face;
    }

    public float getGunPointX() {
        return gunPointX;
    }

    public float getGunPointY() {
        return gunPointY;
    }

    public Bullets getShotsFired() {
        return shotsFired;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }
    
    public boolean canAttack() {
        return canAttack;
    }
    
    public Rectangle bounds() {
        return new Rectangle
        ((int)this.getOffsetX(), (int)this.getOffsetY(), 
                Constants.ALIEN_WIDTH, Constants.ALIEN_HEIGHT);
    }

}
