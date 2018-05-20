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
public class Bullets{
    private ArrayList<Bullet> bulletsFired;
    private int direction;
    
    public Bullets(int direction) {
        bulletsFired = new ArrayList();
        this.direction = direction;
    }
    
    public void addBullet(Bullet bullet) {
        bulletsFired.add(bullet);
    }
    
    public ArrayList<Bullet> getList() {
        return bulletsFired;
    }
    
    public void cleanList() {
        ArrayList<Bullet> temp = new ArrayList();
        
        for(Bullet bullet: bulletsFired)
            if(bullet.isActive())
                temp.add(bullet);
        
        bulletsFired.clear();
        bulletsFired = temp;
    }
    
    public void updateBullets() {
        for(Bullet single: this.bulletsFired)
            single.updateBullet(direction);
        
        this.cleanList();
    }
}
