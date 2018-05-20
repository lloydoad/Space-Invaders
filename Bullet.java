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
public class Bullet {
    private int posX;
    private int posY;
    private int offsetY, velY = 0;
    
    private Image face;
    private SpriteBase scene;
    private boolean active;
    
    public Bullet(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        
        init();
    }
    
    private void init() {
        offsetY = posY + velY;
        active = true;
        
        scene = new SpriteBase("bullet.png", posX, posY, 3, 5);
        face = scene.getFace();
    }
    
    public void updateBullet(int direction) {
        if(direction == Constants.UP) {
            if(offsetY <= 0)
                disable();
            else {
                velY -= Constants.B_VEL;
                offsetY = posY + velY;
            }
        } else if (direction == Constants.DOWN) {
            if(offsetY >= 800)
                disable();
            else {
                velY += Constants.ALIEN_B_VEL;
                offsetY = posY + velY;
            }
        }
    }

    public Image getFace() {
        return face;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void disable() {
        active = false;
    }
    
    public Rectangle bounds() {
        return new Rectangle (this.getPosX(),this.getOffsetY(), 3, 5);
    }
}
