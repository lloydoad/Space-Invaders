/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.*;
import java.awt.*;
import core.SpriteBase;
import core.Constants;
import java.awt.event.*;

/**
 *
 * @author NASS
 */
public class MainMenu extends Panel implements ActionListener, MouseListener {
    private String name;
    private final Image background;
    private SpriteBase newGameButton, highScoreButton, quitButton;
    private final int longWidth = 240, shortWidth = 130, height = 50;
    private final int nx = 170, hx = 562, qx = 420;
    private final int ny = 500, hy = 550, qy = 620;
    
    ////// CONTROLS
    private final Timer control;
    
    ////// BUTTONS
    private final JButton newGameSwitch;
    private final JButton highScoreSwitch;
    private final JButton quitSwitch;
    
    public MainMenu(ActionListener listener) {
        // setup main panel
        name = "main menu";
        this.setMinimumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        addMouseListener(this);
        
        // setup buttons
        newGameSwitch = new JButton("new game");
        newGameSwitch.addActionListener(listener);
        highScoreSwitch = new JButton("high score");
        highScoreSwitch.addActionListener(listener);
        quitSwitch = new JButton("quit");
        quitSwitch.addActionListener(listener);
        
        // setup layeredpane
        SpriteBase temp = new SpriteBase
        ("mainMenu.png", 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        background = temp.getFace();
   
        newGameButton = new SpriteBase
            ("newGameButton.png", nx, ny, longWidth, height);
        highScoreButton = new SpriteBase
            ("highScoreButton.png", hx, hy, longWidth, height);
        quitButton = new SpriteBase
            ("quitButton.png", qx, qy, shortWidth, height);
        
        // setup control
        control = new Timer(5, this);
        control.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        
        // draw background
        g.drawImage(background, 0, 0, null);
        
        // draw buttons
        g.drawImage(newGameButton.getFace(), newGameButton.getPosX(), newGameButton.getPosY(), null);
        g.drawImage(highScoreButton.getFace(), highScoreButton.getPosX(), highScoreButton.getPosY(), null);
        g.drawImage(quitButton.getFace(), quitButton.getPosX(), quitButton.getPosY(), null);
        
        // clean up
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        // new game
        if(x >= nx && x <= nx + longWidth && y >= ny && y <= ny + height) {
            System.out.println("Clicked new game");
            newGameSwitch.doClick();
        }
        // display highscores
        else if(x >= hx && x <= hx + longWidth && y >= hy && y <= hy + height) {
            System.out.println("Clicked highscore");
            highScoreSwitch.doClick();
        }
        // quit game
        else if(x >= qx && x <= qx + shortWidth && y >= qy && y <= qy + height) {
            System.out.println("Clicked quit");
            quitSwitch.doClick();
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
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
    public String getName() {
        return name;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
