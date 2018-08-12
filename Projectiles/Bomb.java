/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projectiles;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author rhuan
 */
public class Bomb extends Pane{
    
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<Bomb> bombsToRemove = new ArrayList<>();
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x;
    int y;
    int damage;
    boolean alive;
    int timer = 2000;
    long timeIndex;
    
    //Create sprite for bomb
    public Bomb(int posX, int posY, Pane gameRoot, int dmg){
        Image bombImage = new Image("");
        ImageView bombIV = new ImageView(bombImage);
        this.iv = bombIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.alive = true;
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.damage = dmg;
        this.getChildren().add(iv);
        bombs.add(this);
        timeIndex = System.currentTimeMillis();
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    
    public void explode(){
        long timeNow = System.currentTimeMillis();
        if(timeNow-timer >= timeIndex){
            //add explode code here
            //add bomb to remove arraylist
        }
        //add code to explode on enemy collision
    }
}
