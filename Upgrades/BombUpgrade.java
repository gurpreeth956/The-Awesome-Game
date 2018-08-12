/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Upgrades;

import javafx.scene.image.Image;

/**
 *
 * @author rhuan
 */
public class BombUpgrade extends Upgrades{
    
    public BombUpgrade(){
        super("file:src/Sprites/BombUpgrade.png", 100);
        cost = 100;
    }
        
    public void activeAbility(Game.Character player) {
	player.setBomb(true);
    }
    
    public String getListView() {
        return "Bombs   -   " + cost;
    }
    
    public String getSummary() {
        return "";
    }
    
    public Image getImage() {
        return new Image("file:src/Sprites/BombUpgrade.png");
    }
}
