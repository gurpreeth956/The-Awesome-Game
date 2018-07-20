package Enemies;
import Environment.Portal;
import Game.Character;
import Projectiles.Projectile;

import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x; //Enemy xPos
    int y; //Enemy yPos
    int coin;
    int score;
    int enemySpeed;
    
    Rectangle healthBarOutline;
    Rectangle actualHealth;
    Rectangle lostHealth;
    boolean alive = true;
    int health;
    int totalHealth;

    public Enemy(String img, int health, int coin, int width, int height) {
	Image enemyImage = new Image(img);
	ImageView enemyIV = new ImageView(enemyImage);
        this.iv = enemyIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	this.health = health;
	this.totalHealth = health;
	this.coin = coin;
	this.score = coin;
	this.width = width;
	this.height = height;
        this.getChildren().addAll(iv);
        
	healthBarOutline = new Rectangle(x - 1, y - 6, width + 2, 4);
	healthBarOutline.setFill(Color.TRANSPARENT);
	healthBarOutline.setStroke(Color.BLACK);
	lostHealth = new Rectangle(x, y - 5, width, 3);
	lostHealth.setFill(Color.RED);
	actualHealth = new Rectangle(x, y - 5, width, 3);
	actualHealth.setFill(Color.GREEN);
	actualHealth.toFront();
    }
    
    public void setCharacterView(int offsetX, int offsetY) {
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }
    
    public void hitView(Enemy enemy) {
        enemy.setCharacterView(128, 0);
    }
    
    public void move(Character player, double a, double b) {
	//To be overridden by child classes
    }
    
    public void shoot(Character player, List<Projectile> list, Pane root) {
        //To be overridden by child classes
    }
    
    public void update(Pane root) {
        //To be overridden by child classes
    }
    
    public void moveX(int x, double width) { //x is horizontal speed
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                if(this.x > width - this.width)
                    this.setTranslateX(width - this.width);
                else {
                    this.setTranslateX(this.getTranslateX() + 1);
                    this.x++;
                }
            }
            else  {
                if(this.x < 0)
                    this.setTranslateX(0);
                else {
                    this.setTranslateX(this.getTranslateX() - 1);
                    this.x--;
                }
            }
	    this.healthPos();
        }
    }
    
    public void moveY(int y, double height) { //y is vertical speed
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                if (this.y > height - this.height)
                    this.setTranslateY(height - this.height);
                else {
                    this.setTranslateY(this.getTranslateY() + 1);
                    this.y++;
                }
            }
            else {
                if (this.y < 0)
                    this.setTranslateY(0);
                else {
                    this.setTranslateY(this.getTranslateY() - 1);
                    this.y--;
                }
            }
	    this.healthPos();
        }
    }

    public void setX(int x) {
        this.setTranslateX(x);
        this.x = x;
    }

    public void setY(int y) {
        this.setTranslateY(y);
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEnemySpeed() {
        return enemySpeed;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }
    
    public boolean isAlive() {
	return alive;
    }
    
    public void setAlive(boolean alive) {
	this.alive = alive;
    }
    
    public void hit() {
	health--;
    }
    
    public int getHealth() {
	return health;
    }
    
    public Rectangle updateHealth() {
	actualHealth = new Rectangle(x, y, this.getHealth() * width / this.totalHealth, 3);
	actualHealth.setFill(Color.GREEN);
	return actualHealth;
    }
    
    public void healthPos() {
	actualHealth.setX(this.x);
	actualHealth.setY(this.y - 5);
	lostHealth.setX(this.x);
	lostHealth.setY(this.y - 5);
	healthBarOutline.setX(this.x - 1);
	healthBarOutline.setY(this.y - 6);
    }
    
    public boolean playerColliding(Character player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }
    
    public boolean enemyColliding(List<Enemy> enemies) {
	boolean colliding = false;
	for (Enemy enemy : enemies) {
	    if (this.x == enemy.x && this.y == enemy.y) continue;
	    if (this.getBoundsInParent().intersects(enemy.getBoundsInParent())){
		colliding = true;
	    }
	}
	return colliding;
    }
    
    public int getCoin() {
	return coin;
    }
    
    public int getScore() {
	return score;
    }
    
    public void summon(Portal portal) {
	this.setTranslateX(portal.getX());
        this.setTranslateY(portal.getY());
        this.x = portal.getX();
        this.y = portal.getY();
    }
    
    public void setOffsetX(int offsetX) {
	this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
	this.offsetY = offsetY;
    }
    
    public Rectangle getHealthBarOutline() {
        return healthBarOutline;
    }
    
    public Rectangle getActualHealth() {
        return actualHealth;
    }
    
    public Rectangle getLostHealth() {
        return lostHealth;
    }
}
