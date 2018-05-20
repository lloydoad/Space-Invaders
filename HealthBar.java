/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author nass_mac
 */
public class HealthBar {
    private ArrayList<SpriteBase> bar;
    private int lives;
    private int spacing = 10, x;
    
    public HealthBar(int lives) {
        this.lives = lives;
        bar = new ArrayList();
        
        init();
    }
    
    private void init() {
        for(int a = 0; a < lives; a++) {
            // public SpriteBase(String src, int posX, int posY, int width, int height)
            x = (a * (Constants.ALIEN_WIDTH - 10)) + (a * spacing) + 30;
            
            SpriteBase temp = new SpriteBase
            ("space.png", x, Constants.BAR_Y, 
                    Constants.ALIEN_WIDTH - 10, Constants.ALIEN_HEIGHT - 10);
            
            bar.add(temp);
        }
    }
    
    public void reduceHealth() {
        if(bar.size() > 0)
            bar.remove(bar.size() - 1);
    }

    public ArrayList<SpriteBase> getBar() {
        return bar;
    }
    
    
}
