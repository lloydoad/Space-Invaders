/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author NASS
 */
public class SpriteBase {
    private Image face;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean loaded = false;
    
    public SpriteBase(String src, int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        face = getImage(src, width, height);
    }
    
    private Image getImage(String name, int w, int h) {
        String link =  "../images/" + name;
        Image picture = null;
        
        // get valid url of image
        URL imgURL = getClass().getResource(link);
        if(imgURL == null)
            System.out.println("Warning: URL failed");

        // if url isn't url, get image from url
        if(imgURL != null) {
            ImageIcon temp = new ImageIcon(imgURL);
            temp = imageResize(temp, w, h);
            picture = temp.getImage();
        }
        else
            System.out.printf("Image %s not found\n", name);
        
        if(picture != null)
            loaded = true;
        else
            System.out.println("Image loading failed");
        
        return picture;
    }
    
    private ImageIcon imageResize(ImageIcon icon, int w, int h)
    {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }

    public Image getFace() {
        return face;
    }
    
    public void setImage(Image newFace) {
        this.face = newFace;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}