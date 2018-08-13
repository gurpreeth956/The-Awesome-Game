package Enemies;
import Game.Character;
import Projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Spikes extends Pane {
    
    public static List<Spikes> spikes = new ArrayList<>();
    public static List<Spikes> spikeToRemove = new ArrayList<>();
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 28;
    int height = 28;
    int x; //Spike xPos
    int y; //Spike yPos
    int damage;
    boolean alive;

    public Spikes(int posX, int posY, Pane gameRoot, int dmg) {
        Image spikeImage = new Image("file:src/Sprites/Spikes.png");
        ImageView spikeIV = new ImageView(spikeImage);
        this.iv = spikeIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.alive = true;
        this.setTranslateX(posX);
	this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.damage = dmg;
        this.getChildren().addAll(iv);
        spikes.add(this);
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public boolean playerShotColliding(List<Projectile> projs) {
        boolean colliding = false;
        for (Projectile proj : projs) {
            if (this.getBoundsInParent().intersects(proj.getBoundsInParent())) {
                alive = false;
                proj.setAlive(false);
                colliding = true;
            }
        }
        return colliding;
    }
    
    public boolean playerColliding(Character player) {
        boolean colliding = false;
        for (Rectangle playerRect : player.getCollisionRects()) {
            if (this.getBoundsInParent().intersects(playerRect.getBoundsInParent())) {
                alive = false;
                colliding = true;
            }
        }
        return colliding;
    }
    
    public int getDamage() {
        return damage;
    }
}
