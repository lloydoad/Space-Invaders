/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author NASS
 */
public class MapGenerator {
    private int row, col;
    private int ySpacing = 50, xSpacing = 90;
    private int alienCount = 0;
    private Alien map[][];
    
    public MapGenerator(int row, int col) {
        map = new Alien[row][col];
        
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                int x = 80 + (ySpacing * i);
                int y = 90 + (xSpacing * j);
                
                map[i][j] = new Alien(y, x);
                map[i][j].setState(Constants.ALIVE);
                alienCount++;
                
                if(i == row - 1)
                    map[i][j].setCanAttack(true);
                // System.out.println("Alien put int - " + i + " " + j);
            }
        }
    }

    public void setxSpacing(int xSpacing) {
        this.xSpacing = xSpacing;
    }

    public void setySpacing(int ySpacing) {
        this.ySpacing = ySpacing;
    }
    
    public Alien getAlien(int index_i, int index_j) {
        return map[index_i][index_j];
    }
    
    public void killAlien(int index_i, int index_j) {
        map[index_i][index_j].setState(Constants.DEAD);
        map[index_i][index_j].setCanAttack(false);
        alienCount--;
        
        // allow following alien to shoot
        if(index_i > 0) {
            map[index_i - 1][index_j].setCanAttack(true);
        }
    }
    
    public boolean areDefeated() {
        if(alienCount <= 0)
            return true;
        else 
            return false;
    }
    
    public Alien[][] getMap() {
        return map;
    }
}

