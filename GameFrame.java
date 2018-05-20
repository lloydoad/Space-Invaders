/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Dimension;
import javax.swing.*;
import core.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author NASS
 */
public class GameFrame implements ActionListener{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu game;
    private JMenu help;
    private JMenuItem newButton;
    private JMenuItem highScore;
    private JMenuItem exitButton;
    private JMenuItem rulesButton;
    private JMenuItem aboutButton;
    
    //////// Game play
    private int row = 5, col = 8;
    private ArrayList<Panel> panels;
    private int highScoreValue = 0;
    private GameMain gameMain;
    private MainMenu mainMenu;
    
    //////// Control buttons
    private JButton newGameButton;
    private JButton highScoreButton;
    private JButton quitButton;
    
    //////// player variables
    private String name = "Unknown";
    
    public GameFrame()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        //setup frame
        frame = new JFrame("Space Invaders");
        frame.setMinimumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        frame.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        frame.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        frame.setResizable(false);
        
        // exit action
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //add menu items
        initMenu();
        newGameButton = new JButton();
        newGameButton.addActionListener(new startNewGame());
        highScoreButton = new JButton();
        highScoreButton.addActionListener(new showHighScores());
        quitButton = new JButton();
        quitButton.addActionListener(new quitGame());
        frame.setLocationRelativeTo(null);
        
        // setup panel holder
        panels = new ArrayList();
        
        // setup main menu
        mainMenu = new MainMenu(this);
        panels.add(mainMenu);
        frame.add(mainMenu, 0);
        
        //show frame
        frame.setVisible(true);
    }
    
    private void initMenu()
    {
        menuBar = new JMenuBar();
        
        game = new JMenu("Game");
        help = new JMenu("Help");
        
        newButton = new JMenuItem("New");
        newButton.setMnemonic('n');
        newButton.addActionListener(new startNewGame());
        exitButton = new JMenuItem("Quit");
        exitButton.setMnemonic('e');
        exitButton.addActionListener(new quitGame());
        highScore = new JMenuItem("High Score");
        highScore.setMnemonic('h');
        highScore.addActionListener(new showHighScores());
        game.add(newButton);
        game.add(highScore);
        game.add(exitButton);
        
        rulesButton = new JMenuItem("Rules");
        rulesButton.setMnemonic('r');
        rulesButton.addActionListener(new showRules());
        aboutButton = new JMenuItem("About");
        aboutButton.setMnemonic('a');
        aboutButton.addActionListener(new showAbout());
        help.add(rulesButton);
        help.add(aboutButton);
        
        menuBar.add(game);
        menuBar.add(help);
        
        frame.setJMenuBar(menuBar);
    } 
    
    private void startGame() {
        String temp = null;
        temp = JOptionPane.showInputDialog("Enter your name (or not)");
        if(temp == null || temp.equals(""))
            temp = "Unknown";
        
        for(Panel singlePanel: panels) { 
            singlePanel.stopPanel();
            frame.remove(singlePanel);
        }

        gameMain = new GameMain(row, col);
        gameMain.setPlayerName(temp);
        panels.add(gameMain);
        frame.add(gameMain);
        gameMain.requestFocusInWindow();
        gameMain.startPanel();
    }
    
    private void pause() {
        for(Panel singlePanel: panels) { 
                singlePanel.stopPanel();
                singlePanel.setVisible(true);
        }
    }
    
    private void play() {
        for(Panel singlePanel: panels) { 
            singlePanel.startPanel();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Recieved from main menu");
        System.out.println("*" + e.getActionCommand() + "*");
        
        if(e.getActionCommand().equals("new game")) {
            newGameButton.doClick();
        } else if(e.getActionCommand().equals("high score")) {;
            highScoreButton.doClick();
        } else if(e.getActionCommand().equals("quit")) {
            quitButton.doClick();
        }
    }
    
    private class startNewGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pause();
            startGame();
        }
    }
    
    private class showHighScores implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Show highscore window");
        }
    }
    
    private class quitGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pause();
            int response = JOptionPane.showConfirmDialog(null, "Are you leaving earth defenseless?", "Quit",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION)
                System.exit(0);
            else
                play();
        }
    }
    
    public class showAbout implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "Space Invaders 3.2\n"
                    + "Lloyd O.A. Dapaah\n\n"
                    + "*Disclaimer: Images were taken from [find git repository]";
            pause();
            int response = JOptionPane.showConfirmDialog(null, message, "Okay", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(response == JOptionPane.OK_OPTION || response == JOptionPane.CANCEL_OPTION)
                play();
        }  
    }
    
    public class showRules implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "CONTROL"
                    + "\nRight arrow key: move right"
                    + "\nLeft arrow key: move left"
                    + "\nSpace key: shoot"
                    + "\nLeft control key: pause game"
                    + "\n"
                    + "\nHint: Bullets cancel out eachother";
            
            pause();
            int response = JOptionPane.showConfirmDialog(null, message, "Okay", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(response == JOptionPane.OK_OPTION || response == JOptionPane.CANCEL_OPTION)
                play();
        }  
    }

}
