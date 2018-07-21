package Upgrades;
import Game.Character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Upgrades extends Pane {
    
    ImageView iv;
    boolean active;
    boolean purchased;
    int cost;
    int width = 31;
    int height = 31;
    
    public Upgrades(String img, int price) {
	Image downImage = new Image(img);
	ImageView downIV = new ImageView(downImage);
	this.iv = downIV;
	this.iv.setViewport(new Rectangle2D(0, 0, this.width, this.height));
	cost = price;
	active = false;
	purchased = false;
	this.getChildren().addAll(iv);
    }
    
    public void setActive(boolean a) {
	active = a;
    }
    
    public void setBought(boolean a) {
	purchased = a;
    }
    
    public boolean getBought() {
        return purchased;
    }
    
    public boolean isActive() {
	return active;
    }
    
    public int getPrice() {
	return cost;
    }
    
    public void activeAbility(Character player) {
	//To be overridden by child classes
    }
    
    public String getListView() {
        //To be overridden by child classes
        return "Upgrade   -   " + cost;
    }
    
    public String getSummary() {
        //To be overridden by child classes
        return "";
    }
    
    public Image getImage() {
        //To be overridden by child classes
        return new Image("");
    }
}
