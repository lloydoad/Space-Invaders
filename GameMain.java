/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import core.*;
/**
 *
 * @author NASS
 */
public class GameMain extends Panel implements KeyListener, ActionListener{
    ///////// MAIN
    private String name;
    private Image background;
    private SpriteBase gameOverScene;
    private SpriteBase winScene;
    private boolean closeOut = false;
    private Timer control;
    private boolean gameOver = false;
    private boolean paused = false;
    
    ///////// ALIENS
    private MapGenerator map;
    private int row, col;
    
    //////// PLAYER
    private String playerName;
    private Player player;
    private Bullets shots;
    private HealthBar health;
    
    public GameMain(int row, int col) {
        this.row = row;
        this.col = col;
        
        //////// ALIENS
        map = new MapGenerator(row, col);
        
        //////// PLAYER
        player = new Player
        ((Constants.SCREEN_WIDTH / 2) - (Constants.PLAYER_WIDTH / 2), 700);
        shots = new Bullets(Constants.UP);
        health = new HealthBar(player.getHealth());
        
        //////// MAIN CONTROLS
        name = "game main";
        control = new Timer(5, this);
        gameOverScene = new SpriteBase
        ("gameOver.png", 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        winScene = new SpriteBase
        ("win.png", 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        
        //////// Setup background
        SpriteBase backSprite = new SpriteBase
        ("bg.png", 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        background = backSprite.getFace();
        
        //////// main control setup
        control.start();
        initControls();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // making images smooth
        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        
        // draw background
        g.drawImage(background, 0, 0, null);
            
        // draw aliens
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                Alien temp = (Alien) map.getMap()[i][j];
                
                if(temp == null) {
                    System.out.printf("[%d,%d] is null\n", i, j);
                } else {
                    if(temp.getState() == Constants.ALIVE) {
                        g.drawImage(temp.getFace(), (int)temp.getOffsetX(), (int)temp.getOffsetY(), null);
                        
                        // draw alien bullets
                        for(Bullet shot: temp.getShotsFired().getList()) {
                            g.drawImage(shot.getFace(), shot.getPosX(), shot.getOffsetY(), this);
                        }
                    }
                }
            }
        }
        
        // draw health bar
        for(int b = 0; b < health.getBar().size(); b++) {
            SpriteBase temp = health.getBar().get(b);
            g.drawImage(temp.getFace(), temp.getPosX(), temp.getPosY(), this);
        }
        
        // draw score label and GameOver display
        this.setForeground(Color.lightGray);
        this.setFont(new Font("Futura", Font.PLAIN, 26));
        g.drawString("Score: " + player.getScore(), 820, Constants.BAR_Y + Constants.ALIEN_HEIGHT - 11);
        
        // if(gameOver) paint, sleep,
        if(map.areDefeated()) {
            g.drawImage(winScene.getFace(), 0, 0, this);
            control.stop();
            closeOut = true;
        }
        else if(gameOver) {
            g.drawImage(gameOverScene.getFace(), 0, 0, this);
            control.stop();
            closeOut = true;
        }
        
        // draw player
        if(player.isAlive()) {
            g.drawImage(player.getFace(), player.getPosX(), player.getPosY(), this);
        
            // draw fired bullets
            for(Bullet shot: shots.getList())
                g.drawImage(shot.getFace(), shot.getPosX(), shot.getOffsetY(), this);
        }   
        
        // clean up
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        // if player dies/ all aliens die/ stop updating values
        if(player.isAlive() == false || map.areDefeated()) {
            System.out.println("High score: " + player.getScore());
            gameOver = true;
            System.out.println("High score: " + player.getScore());
        }
        
        if(gameOver == false)
            for(int i = 0; i < row; i++)
                for(int j = 0; j < col; j++)
                    if(map.getAlien(i, j) != null)
                        map.getAlien(i, j).updateAlien();
        
        // check if human shots and aliens intercept
        // cycle through bullets and check with alien
        alien_bullet_collision();
        
        // check if alien shots and human intercept
        player_bullet_collision();
        
        // update whether game should accept keyboard inputs
        if(player.isAlive() == false || gameOver == true) {
            this.setFocusable(false);
            this.removeKeyListener(this);
        }

        if(gameOver == false) {
            // reduce score multiplier over time
            player.decreaseScoreMult();
            // update coordinate of screen bullets and garbage collect
            shots.updateBullets();   
        }
        repaint();
    }
    
    private void initControls() {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        // right and left key triggers
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(paused == false)
                player.moveRight();
        } else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
            if(paused == false)
                player.moveLeft();
        }
        
        // shoot key trigger
        if(ke.getKeyCode() == KeyEvent.VK_SPACE) {
            shots.addBullet(new Bullet(player.getGunPointX(), player.getPosY()));
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_CONTROL) {
            if(paused == false) {
                paused = true;
                control.stop();
            }
            else {
                paused = false;
                control.start();
            }
        }
    }
    
    private void player_bullet_collision() {
        // cycle through alive aliens who can shoot
        // cycle through bullets in list
        // check if they collide
        // if they do, reduce health
        Rectangle playerBounds = player.bounds();
        Rectangle shotBound;
        
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++) {
                Alien temp = map.getAlien(i, j);
                
                if(temp.getState() == Constants.ALIVE && temp.canAttack()) {
                    for(Bullet shot: temp.getShotsFired().getList()) {
                        if(shot.isActive() == false)
                            break;
                        
                        shotBound = shot.bounds();
                        if(playerBounds.intersects(shotBound)) {
                            player.reduceHealth();
                            health.reduceHealth();
                            shot.disable();
                        }   
                    }
                }
            }
    }
    
    private void alien_bullet_collision() {
        // check if human shots and aliens intercept
        // cycle through bullets and check with alien
        Rectangle bulletBounds = null;
        Rectangle alienBounds = null;
        
        for(Bullet bullet: shots.getList()) {
            if(bullet.isActive() == false)
                break;
            else
                bulletBounds = bullet.bounds();
            
            for(int i = 0; i < row; i++)
                for(int j = 0; j < col; j++){
                    Alien temp = map.getAlien(i, j);

                    if(temp.getState() == Constants.ALIVE) {
                        alienBounds = map.getAlien(i, j).bounds();
                    
                        if(bulletBounds.intersects(alienBounds)) {
                            map.killAlien(i, j);
                            player.increaseScore();
                            bullet.disable();
                            
                            // System.out.println("Mult: " + player.getScoreMult());
                        }
                    }
                }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private boolean isGameOver() {
        return gameOver;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void stopPanel() {
        control.stop();
        this.setVisible(false);
    }
    
    @Override
    public void startPanel() {
        control.start();
        this.setVisible(true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {}
    
    @Override
    public void keyTyped(KeyEvent ke) {}
}
