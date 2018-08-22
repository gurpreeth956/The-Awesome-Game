package Projectiles;
import Enemies.Enemy;
import Game.Character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Projectile extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x; //Proj xPos
    int y; //Proj yPos
    int damage;
    
    int velocityX = 0;
    int velocityY = 0;
    boolean alive = true;
    
    public Projectile(String img, int posX, int posY, int width, int height, int dmg) {
        Image projImage = new Image(img);
	ImageView projIV = new ImageView(projImage);
        this.iv = projIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;
        this.damage = dmg;
        this.getChildren().addAll(iv);
    }
    
    public void move(Character player) {
        this.setTranslateX(this.getTranslateX() + this.getVelocityX());
        this.setTranslateY(this.getTranslateY() + this.getVelocityY());
    }
    
    public int getVelocityX() {
        return velocityX;
    }
    
    public int getVelocityY() {
        return velocityY;
    }
    
    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }
    
    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public boolean enemyColliding(Enemy enemy) {
        if (!enemy.hasCollisionRects()) {
            return this.getBoundsInParent().intersects(enemy.getBoundsInParent());
        }
        
        for (Rectangle rect : enemy.getCollisionRects()) {
            if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean playerColliding(Character player) {
        for (Rectangle rect : player.getCollisionRects()) {
            if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
    
    public int getDamage() {
        return damage;
    }
}
