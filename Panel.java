/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author nass_mac
 */
public abstract class Panel extends JPanel {
    public abstract String getName();
    public abstract void stopPanel();
    public abstract void startPanel();
}
